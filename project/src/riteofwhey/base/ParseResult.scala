package riteofwhey.base

//assert all not null

//todo: sets are verbose, use seq instead?

abstract sealed class ParseResult[T] extends NotNull

//assert: reslut not null
case class Result[T](val result:T, val warning:Set[Promlem]=Set[Promlem]()) extends ParseResult[T]//, val warnings=Set[Promlem]())

//assert: errors not empty
case class Errors[T](val errors:Set[Promlem])  extends ParseResult[T]

//todo: notion of severity?
//assert: msg not empty
//assert: locs nonempty, non overlaping
case class Promlem(val msg:String, val locs: Set[OffsetRange]) extends NotNull

//todo: problems should have an enumerated type

//TODO:assertstart<end
//assert start &end >0
//assert start end < total
case class OffsetRange(val start:Int, val end:Int) extends NotNull

//todo: are char pointers too limiting?