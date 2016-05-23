package demo.utils

import play.api.mvc.QueryStringBindable

object AddressBinder {
  def binder[A, T1, T2, T3](key1: String, key2: String, key3: String)
                           (constructor: (T1, T2, T3) => A, destructor: A => Option[(T1, T2, T3)])
                           (implicit elemOneBinder:   QueryStringBindable[T1],
                                     elemTwoBinder:   QueryStringBindable[T2],
                                     elemThreeBinder: QueryStringBindable[T3],
                                     manifest: Manifest[A]
                           ) =
    new QueryStringBindable[A] {
      override def bind(key: String, params: Map[String, Seq[String]]) =
        for {
          elemOne   <- elemOneBinder.bind(key1, params)
          elemTwo   <- elemTwoBinder.bind(key2, params)
          elemThree <- elemThreeBinder.bind(key3, params)
        } yield {
          (elemOne, elemTwo, elemThree) match {
            case (Right(e1), Right(e2), Right(e3)) => Right(constructor(e1, e2, e3))
            case _ => Left(s"Unable to bind a $manifest")
          }
        }

      override def unbind(key: String, m: A) = {
        val Some((e1, e2, e3)) = destructor(m)
        Seq(elemOneBinder.unbind(key1, e1), elemTwoBinder.unbind(key2, e2), elemThreeBinder.unbind(key3, e3)).filterNot(_.isEmpty).mkString("&")
      }
    }
}

case class Address(street: String, city: String, country: String) {
  override def toString = List(street, city, country).mkString(",\n")
}

object Address {
  implicit val binder = AddressBinder.binder("street", "city", "country")(Address.apply, Address.unapply)
}