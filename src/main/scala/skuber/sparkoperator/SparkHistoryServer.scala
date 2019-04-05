package skuber.sparkoperator

import skuber.ResourceSpecification.{Names, Scope}
import skuber._

case class SparkHistoryServer(
                             kind: String = "SparkHistoryServer",
                             override val apiVersion: String = "sparkoperator.k8s.io/v1beta1",
                             metadata: ObjectMeta = ObjectMeta(),
                             spec: Option[SparkHistoryServer.Spec] = None
                           ) extends ObjectResource

object SparkHistoryServer {
  object Type extends Enumeration {
    type Type = Value
    val sharedVolume, remoteStorage = Value
  }

  case class SharedVolume(
                          size: String = "0.3Gi",
                          mountPath: String = "/history/spark-events",
                          matchLabels: Map[String,String] = Map()
                          )

  case class Kerberos(
                     enabled: Boolean = true,
                     principal: String = "",
                     keytab: String = ""
                     )

  case class Cleaner(
                    enabled: Boolean = false,
                    interval: Int = 1,
                    maxAge: Int = 7
                    )

  case class Spec(
                 `type`: SparkHistoryServer.Type.Value = SparkHistoryServer.Type.sharedVolume,
                 sharedVolume: Option[SparkHistoryServer.SharedVolume] = None,
                 sparkConfiguration: List[EnvVar] = List(),
                 remoteURI: String = "",
                 expose: Boolean = false,
                 host: String = "",
                 customImage: String = "",
                 logDirectory: String = "file:/history/spark-events",
                 updateInterval: String = "10",
                 internalPort: String = "18080",
                 retainedApplications: String = "50",
                 maxApplications: String = "999999",
                 provider: String = "org.apache.spark.deploy.history.FsHistoryProvider",
                 kerberos: Option[SparkHistoryServer.Kerberos],
                 cleaner: Option[SparkHistoryServer.Cleaner],
                 endEventReparseChunkSize: Int = 1,
                 inProgressOptimization: Boolean = true,
                 numReplayThreads: String = "",
                 maxDiskUsage: Int = 10,
                 persistentPath: String = ""
                 )

  val specification = NonCoreResourceSpecification(
    apiGroup = "sparkoperator.k8s.io",
    version = "v1beta1",
    scope = Scope.Namespaced,
    names = Names(
      plural = "sparkhistoryserver",
      singular = "sparkhistoryserver",
      kind = "SparkHistoryServer",
      shortNames = List()
    )
  )

  implicit val sparkHistoryServerDef = new ResourceDefinition[SparkHistoryServer] { def spec=specification }
  implicit val sparkHistoryServerListDef = new ResourceDefinition[ListResource[SparkHistoryServer]] { def spec=specification }
}