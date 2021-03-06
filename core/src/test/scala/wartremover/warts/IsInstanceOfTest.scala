package org.wartremover
package test

import org.scalatest.FunSuite

import org.wartremover.warts.IsInstanceOf

class IsInstanceOfTest extends FunSuite with ResultAssertions {
  test("isInstanceOf is disabled") {
    val result = WartTestTraverser(IsInstanceOf) {
      "abc".isInstanceOf[String]
    }
    assertError(result)("isInstanceOf is disabled")
  }
  test("isInstanceOf wart obeys SuppressWarnings") {
    val result = WartTestTraverser(IsInstanceOf) {
      @SuppressWarnings(Array("org.wartremover.warts.IsInstanceOf"))
      val foo = "abc".isInstanceOf[String]
    }
    assertEmpty(result)
  }
  test("isInstanceOf should not check macro expansions") {
    val result = WartTestTraverser(IsInstanceOf) {
      IsInstanceOfTestMacros.is[Object, String]
    }
    assertEmpty(result)
  }
}
