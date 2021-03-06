package com.ljl.scala.implictconvert

import scala.reflect.{ClassTag, classTag}


class Printer[T: ClassTag] {

    implicit val tClassTag = classTag[T]

    def print(t: T)(implicit tClassTag: ClassTag[T]): Unit = {
        println(tClassTag)

        val fields = tClassTag.runtimeClass.getDeclaredFields
        for (field <- fields) {
            field.setAccessible(true)
            println(field.getName + "," + field.get(t))
        }
    }
}

case class Person(id: String, name: String)

/**
  * 隐式转换使用的时机：类型不匹配时，方法不存在时
  */
object ImplictConvertDemo {

    def main(args: Array[String]): Unit = {

        //1. 隐式值及其使用
        implicit val name = "smith"
        //无论是隐式函数还是隐式值都是根据类型来匹配的，
        // 所以在上下文不能存在类型相同的两个隐式值或隐式函数，否则
        //Error:(20, 9) ambiguous implicit values:
        // both value gender of type String
        // and value name of type String
        // match expected type String
        //        sayHello //smith
        //        implicit val gender = "female"

        //会在上下文查找名称为name的隐式值赋给name
        //具体赋值，隐式值，默认值的优先级依次降低

        def sayHello(implicit name: String = "defalut"): Unit = println(name)

        sayHello("mark") //mark
        sayHello //smith

        val printer = new Printer[Person]
        printer.print(Person("11", "oliver"))

    }

}
