package scala

import java.io.{BufferedReader, File, FileOutputStream, InputStreamReader}
import com.amazonaws.AmazonServiceException;
import com.amazonaws.regions.Regions;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.S3Object;
import com.amazonaws.services.s3.model.S3ObjectInputStream;

class S3Dao {
  val s3: AmazonS3 =
    AmazonS3ClientBuilder.defaultClient()
  val BUCKET_NAME: String = "pokeanalytics-data"
  val DATA_WAREHOUSE: String = "datawarehouse"
  val localWarehouse: String = ""
  val locaLakePath: String = ""

  downloadFromBucket("test")

  // download file from S3 and write to local file
  def downloadFromBucket(filename: String): Unit = {
    println(
      "Downloading %s from S3 bucket %s...\n",
      DATA_WAREHOUSE,
      BUCKET_NAME
    )
    try {
      val s3Object: S3Object = s3.getObject(BUCKET_NAME, DATA_WAREHOUSE)
      val s3InputStream: S3ObjectInputStream = s3Object.getObjectContent()
      val fos: FileOutputStream =
        new FileOutputStream(new File(DATA_WAREHOUSE));
      var read_buf = Array[Byte]()
      var read_len: Int = s3InputStream.read(read_buf)
      while (read_len > 0) {
        fos.write(read_buf, 0, read_len);
        read_len = s3InputStream.read(read_buf)
      }
      s3InputStream.close()
      fos.close()
    } catch {
      case e: Throwable => {
        println(e.getMessage())
        System.exit(1)
      }
    }
  }
}
