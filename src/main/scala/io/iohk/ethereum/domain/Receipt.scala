package io.iohk.ethereum.domain

import akka.util.ByteString
import io.iohk.ethereum.mpt.ByteArraySerializable
import org.spongycastle.util.encoders.Hex

object Receipt {

  def byteArraySerializable(ethCompatibilityMode: Boolean): ByteArraySerializable[Receipt] = new ByteArraySerializable[Receipt] {
    import io.iohk.ethereum.network.p2p.messages.PV63.ReceiptImplicits._

    override def fromBytes(bytes: Array[Byte]): Receipt = bytes.toReceipt(ethCompatibilityMode)

    override def toBytes(input: Receipt): Array[Byte] = input.toBytes
  }

}

case class Receipt(
                    postTransactionStateHash: ByteString,
                    cumulativeGasUsed: BigInt,
                    logsBloomFilter: ByteString,
                    logs: Seq[TxLogEntry],
                    statusCode: Option[ByteString],
                    returnData: Option[ByteString]
                  ) {
  override def toString: String = {
    s"""
       |Receipt{
       | postTransactionStateHash: ${Hex.toHexString(postTransactionStateHash.toArray[Byte])}
       | cumulativeGasUsed: $cumulativeGasUsed
       | logsBloomFilter: ${Hex.toHexString(logsBloomFilter.toArray[Byte])}
       | logs: $logs
       | statusCode: ${statusCode.map(s => Hex.toHexString(s.toArray[Byte]))}
       | returnData: ${returnData.map(rd => Hex.toHexString(rd.toArray[Byte]))}
       |}
       """.stripMargin
  }
}
