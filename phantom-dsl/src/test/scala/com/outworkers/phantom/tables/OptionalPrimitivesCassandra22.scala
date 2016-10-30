/*
 * Copyright 2013-2017 Outworkers, Limited.
 *
 * All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * - Redistributions of source code must retain the above copyright notice,
 * this list of conditions and the following disclaimer.
 *
 * - Redistributions in binary form must reproduce the above copyright
 * notice, this list of conditions and the following disclaimer in the
 * documentation and/or other materials provided with the distribution.
 *
 * - Explicit consent must be obtained from the copyright owner, Outworkers Limited before any redistribution is made.
 *
 * THIS SOFTWARE IS PROVIDED BY THE COPYRIGHT HOLDERS AND CONTRIBUTORS "AS IS"
 * AND ANY EXPRESS OR IMPLIED WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE
 * IMPLIED WARRANTIES OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE
 * ARE DISCLAIMED. IN NO EVENT SHALL THE COPYRIGHT HOLDER OR CONTRIBUTORS BE
 * LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
 * CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF
 * SUBSTITUTE GOODS OR SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS
 * INTERRUPTION) HOWEVER CAUSED AND ON ANY THEORY OF LIABILITY, WHETHER IN
 * CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE)
 * ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE
 * POSSIBILITY OF SUCH DAMAGE.
 */
package com.outworkers.phantom.tables

import com.outworkers.phantom.CassandraTable
import com.outworkers.phantom.connectors.RootConnector
import com.outworkers.phantom.builder.query.InsertQuery
import com.outworkers.phantom.dsl._
import com.outworkers.util.testing._

case class OptionalPrimitiveCassandra22(
  pkey: String,
  short: Option[Short],
  byte: Option[Byte],
  localDate: Option[LocalDate]
)

object OptionalPrimitiveCassandra22 {

  def none: OptionalPrimitiveCassandra22 = {
    OptionalPrimitiveCassandra22(
      pkey = gen[String],
      short = None,
      byte = None,
      localDate = None
    )
  }
}

sealed class OptionalPrimitivesCassandra22 extends CassandraTable[ConcreteOptionalPrimitivesCassandra22, OptionalPrimitiveCassandra22] {

  object pkey extends StringColumn(this) with PartitionKey[String]

  object short extends OptionalSmallIntColumn(this)

  object byte extends OptionalTinyIntColumn(this)

  object localDate extends OptionalLocalDateColumn(this)

  override def fromRow(r: Row): OptionalPrimitiveCassandra22 = {
    OptionalPrimitiveCassandra22(
      pkey = pkey(r),
      short = short(r),
      byte = byte(r),
      localDate = localDate(r)
    )
  }
}

abstract class ConcreteOptionalPrimitivesCassandra22 extends OptionalPrimitivesCassandra22 with RootConnector {

  override val tableName = "OptionalPrimitivesCassandra22"

  def store(row: OptionalPrimitiveCassandra22): InsertQuery.Default[ConcreteOptionalPrimitivesCassandra22, OptionalPrimitiveCassandra22] = {
    insert
      .value(_.pkey, row.pkey)
      .value(_.short, row.short)
      .value(_.byte, row.byte)
      .value(_.localDate, row.localDate)
  }
}
