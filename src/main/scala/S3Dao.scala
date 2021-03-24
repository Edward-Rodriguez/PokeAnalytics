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
  val DATA_WAREHOUSE: String = "DATAWAREHOUSE/"
  val localWarehouse: String = ""
  val locaLakePath: String = ""

  downloadFromBucket("employee_info.csv")

  // download file from S3 and write to local file
  def downloadFromBucket(filename: String): Unit = {
    println(
      "Downloading %s from S3 bucket %s...\n",
      DATA_WAREHOUSE,
      BUCKET_NAME
    )
    var s3Object = None: Option[S3Object]
    var s3InputStream = None: Option[S3ObjectInputStream]
    var fos = None: Option[FileOutputStream]
    try {
      s3Object = Some(s3.getObject(BUCKET_NAME, DATA_WAREHOUSE + filename))
      s3InputStream = Some(s3Object.get.getObjectContent())
      fos = Some(new FileOutputStream(new File(filename)));
      var read_buf = new Array[Byte](1024)
      var read_len: Int = s3InputStream.get.read(read_buf)
      println("read_len = " + read_len)
      while (read_len > 0) {
        println("Hello there..")
        fos.get.write(read_buf, 0, read_len);
        read_len = s3InputStream.get.read(read_buf)
      }

    } catch {
      case e: Throwable => {
        println(e.getMessage())
        System.exit(1)
      }
    } finally {
      if (s3Object.get != null) {
        s3InputStream.get.abort()
        s3Object.get.close()
        fos.get.close()
      }
    }
  }
}
