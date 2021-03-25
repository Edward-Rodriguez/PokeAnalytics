package scala

import java.io.{BufferedReader, File, FileOutputStream, InputStreamReader}
import com.amazonaws.{AmazonServiceException, SdkClientException}
import com.amazonaws.services.s3.{AmazonS3, AmazonS3ClientBuilder}
import com.amazonaws.services.s3.model.{S3Object, S3ObjectInputStream}
import com.amazonaws.services.s3.model.{PutObjectRequest, ObjectMetadata}

class S3Dao {
  val s3Client: AmazonS3 =
    AmazonS3ClientBuilder.defaultClient()
  val BUCKET_NAME: String = "pokeanalytics-data"
  val DATA_WAREHOUSE: String = "/DATAWAREHOUSE"

  uploadFileToS3Bucket(
    BUCKET_NAME,
    DATA_WAREHOUSE,
    "ditto.JSON",
    "dittoExampleAPI.JSON",
    "application/json",
    "dittoAPI",
    "dittoAPIExample"
  )

  // download file from S3 and write to local file
  def downloadFromBucket(filename: String): Unit = {
    var s3Object = None: Option[S3Object]
    var s3InputStream = None: Option[S3ObjectInputStream]
    var fos = None: Option[FileOutputStream]
    try {
      s3Object = Some(
        s3Client.getObject(BUCKET_NAME, DATA_WAREHOUSE + filename)
      )
      s3InputStream = Some(s3Object.get.getObjectContent())
      fos = Some(new FileOutputStream(new File(filename)));
      var read_buf = new Array[Byte](1024)
      var read_len: Int = s3InputStream.get.read(read_buf)
      while (read_len > 0) {
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

  // upload file to s3 bucket
  def uploadFileToS3Bucket(
      bucketName: String,
      folderName: String,
      fileObjKeyName: String,
      fileName: String,
      contentType: String,
      metadataTitle: String,
      meta_data: String
  ): Unit = {
    try {
      // Upload a file as a new object with ContentType and title specified.
      val request =
        new PutObjectRequest(
          bucketName + folderName,
          fileObjKeyName,
          new File(fileName)
        )
      val metadata = new ObjectMetadata();
      metadata.setContentType(contentType);
      metadata.addUserMetadata(metadataTitle, meta_data);
      request.setMetadata(metadata);
      s3Client.putObject(request);
    } catch {
      case e: AmazonServiceException => {
        // The call was transmitted successfully, but Amazon S3 couldn't process
        // it, so it returned an error response.
        e.printStackTrace();
      }
      case e: SdkClientException => {
        // Amazon S3 couldn't be contacted for a response, or the client
        // couldn't parse the response from Amazon S3.
        e.printStackTrace();
      }
    }
  }

}
