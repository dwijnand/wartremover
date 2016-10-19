package org.wartremover
package test

import org.scalatest.FunSuite

import org.wartremover.warts.CaseClass

class CaseClassTest extends FunSuite {
  test("can't declare case classes") {
    val result = WartTestTraverser(CaseClass) {
      case class Foo(i: Int)
    }
    assertResult(List("case classes are disabled"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }
  test("can declare regular classes") {
    val result = WartTestTraverser(CaseClass) {
      class Foo(i: Int)
    }
    assertResult(List.empty, "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }
  test("can't declare case classes inside other classes") {
    val result = WartTestTraverser(CaseClass) {
      class Outer {
        case class Foo(i: Int)
      }
    }
    assertResult(List("case classes are disabled"), "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }
  test("CaseClass wart obeys SuppressWarnings") {
    val result = WartTestTraverser(CaseClass) {
      @SuppressWarnings(Array("org.wartremover.warts.CaseClass"))
      case class Foo(i: Int)
    }
    assertResult(List.empty, "result.errors")(result.errors)
    assertResult(List.empty, "result.warnings")(result.warnings)
  }
}
