package skuber.sparkoperator

import skuber.ResourceSpecification.{Names, Scope}
import skuber._

case class SparkCluster(
                             kind: String = "SparkCluster",
                             override val apiVersion: String = "sparkoperator.k8s.io/v1beta1",
                             metadata: ObjectMeta = ObjectMeta(),
                             spec: Option[SparkCluster.Spec] = None
                           ) extends ObjectResource

object SparkCluster {
  case class RCSpec(
                   instances: Int = 1,
                   memory: String = "",
                   cpu: String = "",
                   labels: Map[String,String] = Map(),
                   command: List[String] = List(),
                   commandArgs: List[String] = List()
                   )

  object HistoryServerType extends Enumeration {
    type HistoryServerType = Value
    val sharedVolume, remoteStorage = Value
  }

  case class HistoryServerSharedVolume(
                                      size: String = "0.3Gi",
                                      mountPath: String = "/history/spark-events",
                                      matchLabels: Map[String,String] = Map()
                                      )

  case class HistoryServer(
                          name: String = "",
                          `type`: SparkCluster.HistoryServerType.Value = SparkCluster.HistoryServerType.sharedVolume,
                          sharedVolume: Option[SparkCluster.HistoryServerSharedVolume] = None,
                          remoteURI: String = ""
                          )

  case class DownloadData(
                         url: String,
                         to: String,
                         required: Boolean = false,
                         downloadTimeout: Option[Int] = None
                         )

  case class Spec(
                 master: Option[SparkCluster.RCSpec],
                 worker: Option[SparkCluster.RCSpec],
                 customImage: String = "",
                 metrics: Boolean = false,
                 sparkWebUI: Boolean = true,
                 sparkConfigurationMap: String = "",
                 env: List[EnvVar] = List(),
                 sparkConfiguration: List[EnvVar] = List(),
                 historyServer: Option[SparkCluster.HistoryServer],
                 downloadData: List[SparkCluster.DownloadData] = List()
                 )

  val specification = NonCoreResourceSpecification(
    apiGroup = "sparkoperator.k8s.io",
    version = "v1beta1",
    scope = Scope.Namespaced,
    names = Names(
      plural = "sparkclusters",
      singular = "sparkcluster",
      kind = "SparkCluster",
      shortNames = List()
    )
  )
  implicit val sparkClusterDef = new ResourceDefinition[SparkCluster] { def spec=specification }
  implicit val sparkClusterListDef = new ResourceDefinition[ListResource[SparkCluster]] { def spec=specification }
}