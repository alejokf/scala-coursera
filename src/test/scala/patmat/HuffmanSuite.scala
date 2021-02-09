package patmat

import org.junit.runner.RunWith
import org.scalatest.FunSuite
import org.scalatest.junit.JUnitRunner
import patmat.Huffman._

@RunWith(classOf[JUnitRunner])
class HuffmanSuite extends FunSuite {

  trait TestTrees {
    val t1 = Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5)
    val t2 = Fork(Fork(Leaf('a', 2), Leaf('b', 3), List('a', 'b'), 5), Leaf('d', 4), List('a', 'b', 'd'), 9)
  }


  test("weight of a larger tree") {
    new TestTrees {
      assert(weight(t1) === 5)
    }
  }


  test("chars of a larger tree") {
    new TestTrees {
      assert(chars(t2) === List('a', 'b', 'd'))
    }
  }


  test("string2chars(\"hello, world\")") {
    assert(string2Chars("hello, world") === List('h', 'e', 'l', 'l', 'o', ',', ' ', 'w', 'o', 'r', 'l', 'd'))
  }


  test("makeOrderedLeafList for some frequency table") {
    assert(makeOrderedLeafList(List(('t', 2), ('e', 1), ('x', 3))) === List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 3)))
  }


  test("combine of some leaf list") {
    val leaflist = List(Leaf('e', 1), Leaf('t', 2), Leaf('x', 4))
    assert(combine(leaflist) === List(Fork(Leaf('e', 1), Leaf('t', 2), List('e', 't'), 3), Leaf('x', 4)))
  }


  test("decode and encode a very short text should be identity") {
    new TestTrees {
      assert(decode(t1, encode(t1)("ab".toList)) === "ab".toList)
    }
  }

  //-------------------------------------

  test("times(List('a', 'b', 'a'))") {
    assert(times(List('a', 'b', 'a')) === List(('a', 2), ('b', 1)))
  }

  test("singleton") {
    assert(singleton(List(Leaf('e', 1))))
  }

  test("not singleton") {
    assert(!singleton(List(Leaf('e', 1), Leaf('f', 1))))
  }

  test("combine of a singleton or nil") {
    val leaflist = List(Leaf('e', 1))
    assert(combine(leaflist) === leaflist)
  }

  test("encode and decode using a Huffman Tree") {
    val text = "huffmanestcool"
    val myCodeTree = Huffman.createCodeTree(Huffman.string2Chars(text))
    val myEncodedText = Huffman.encode(myCodeTree)(Huffman.string2Chars(text))
    assert(Huffman.decode(myCodeTree, myEncodedText) == Huffman.string2Chars(text))
  }

  test("encode and decode using a Coding Table") {
    val text = "huffmanestcool"
    val myCodeTree = Huffman.createCodeTree(Huffman.string2Chars(text))
    val myEncodedText = Huffman.quickEncode(myCodeTree)(Huffman.string2Chars(text))
    assert(Huffman.decode(myCodeTree, myEncodedText) == Huffman.string2Chars(text))
  }

  test("encoding using a Huffman Tree equals encoding using a Coding Table") {
    val text = "huffmanestcool"
    val myCodeTree = Huffman.createCodeTree(Huffman.string2Chars(text))
    val encodedHuffman = Huffman.encode(myCodeTree)(Huffman.string2Chars(text))
    val encodedCodingTable = Huffman.quickEncode(myCodeTree)(Huffman.string2Chars(text))
    assert(encodedHuffman === encodedCodingTable)
  }

}
