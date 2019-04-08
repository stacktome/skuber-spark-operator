package skuber.sparkoperator

import skuber.ResourceSpecification.{Names, Scope}
import skuber._

//https://github.com/GoogleCloudPlatform/spark-on-k8s-operator/blob/master/pkg/apis/sparkoperator.k8s.io/v1beta1/types.go

case class SparkApplication(
                             kind: String = "SparkApplication",
                             override val apiVersion: String = "sparkoperator.k8s.io/v1beta1",
                             metadata: ObjectMeta = ObjectMeta(),
                             spec: Option[SparkApplication.Spec] = None
                           ) extends ObjectResource

object SparkApplication {
  object Mode extends Enumeration {
    type Mode = Value
    val client, cluster, `in-cluster-client` = Value
  }

  object Type extends Enumeration {
    type Type = Value
    val Java, Scala, Python, R = Value
  }

  case class Deps(
                 jars: List[String] = List(),
                 files: List[String] = List(),
                 pyFiles: List[String] = List(),
                 jarsDownloadDir: List[String] = List(),
                 filesDownloadDir: List[String] = List(),
                 downloadTimeout: Option[Int] = None,
                 maxSimultaneousDownloads: Option[Int] = None
                 )

  case class NamePath(
                     name: String,
                     path: String
                     )

  case class NameKey(
                    name: String,
                    key: String
                    )

  case class SecretInfo(
                       name: String,
                       path: String,
                       secretType: String
                       )

  case class ExecutorSpec(
                         cores: String = "",
                         coreLimit: String = "",
                         memory: String = "",
                         memoryOverhead: String = "",
                         image: String = "",
                         configMaps: List[SparkApplication.NamePath] = List(),
                         secrets: List[SparkApplication.SecretInfo] = List(),
                         envVars: Map[String,String] = Map(),
                         envSecretKeyRefs: Map[String,SparkApplication.NameKey] = Map(),
                         labels: Map[String,String] = Map(),
                         annotations: Map[String,String] = Map(),
                         volumeMounts: List[Volume.Mount] = List(),
                         affinity: Option[Pod.Affinity] = None,
                         tolerations: List[Pod.Toleration] = List(),
                         securityContext: Option[PodSecurityContext] = None,
                         schedulerName: String = "",
                         instances: Option[Int] = None,
                         coreRequest: String = "",
                         javaOptions: String = ""
                         )

  case class DriverSpec(
                       cores: String = "",
                       coreLimit: String = "",
                       memory: String = "",
                       memoryOverhead: String = "",
                       image: String = "",
                       configMaps: List[SparkApplication.NamePath] = List(),
                       secrets: List[SparkApplication.SecretInfo] = List(),
                       envVars: Map[String,String] = Map(),
                       envSecretKeyRefs: Map[String,SparkApplication.NameKey] = Map(),
                       labels: Map[String,String] = Map(),
                       annotations: Map[String,String] = Map(),
                       volumeMounts: List[Volume.Mount] = List(),
                       affinity: Option[Pod.Affinity] = None,
                       tolerations: List[Pod.Toleration] = List(),
                       securityContext: Option[PodSecurityContext] = None,
                       schedulerName: String = "",
                       podName: String = "",
                       serviceAccount: String = "",
                       javaOptions: String = ""
                       )

  case class PrometheusSpec(
                           jmxExporterJar: String,
                           port: Int,
                           configFile: String = "",
                           configuration: String = ""
                           )

  case class MonitoringSpec(
                           exposeDriverMetrics: Boolean,
                           exposeExecutorMetrics: Boolean,
                           metricsProperties: String = "",
                           prometheus: Option[SparkApplication.PrometheusSpec] = None
                           )

  case class RestartPolicy(
                          `type`: Option[skuber.RestartPolicy.Value] = None,
                          onSubmissionFailureRetries: Option[Int] = None,
                          onFailureRetries: Option[Int] = None,
                          onSubmissionFailureRetryInterval: Option[Int] = None,
                          onFailureRetryInterval: Option[Int] = None
                          )

  case class Spec(
                   `type`: SparkApplication.Type.Value,
                   sparkVersion: String = "",
                   mode: Option[SparkApplication.Mode.Value] = None,
                   image: String = "",
                   initContainerImage: String = "",
                   imagePullPolicy: Option[Container.PullPolicy.Value] = None,
                   imagePullSecrets: List[String] = List(),
                   mainClass: String = "",
                   mainApplicationFile: String,
                   arguments: List[String] = List(),
                   sparkConf: Map[String,String] = Map(),
                   hadoopConf: Map[String,String] = Map(),
                   sparkConfigMap: List[String] = List(),
                   hadoopConfigMap: List[String] = List(),
                   volumes: List[Volume.Mount] = List(),
                   driver: Option[SparkApplication.DriverSpec] = None,
                   executor: Option[SparkApplication.ExecutorSpec] = None,
                   deps: Option[SparkApplication.Deps] = None,
                   restartPolicy: Option[SparkApplication.RestartPolicy] = None,
                   nodeSelector: Map[String,String] = Map(),
                   failureRetries: Option[Int] = None,
                   retryInterval: Option[Int] = None,
                   pythonVersion: String = "",
                   memoryOverheadFactor: String = "",
                   monitoring: Option[SparkApplication.MonitoringSpec] = None
                 )

  val specification = NonCoreResourceSpecification(
    apiGroup = "sparkoperator.k8s.io",
    version = "v1beta1",
    scope = Scope.Namespaced,
    names = Names(
      plural = "sparkapplications",
      singular = "sparkapplication",
      kind = "SparkApplication",
      shortNames = List()
    )
  )
  implicit val sparkApplicationDef = new ResourceDefinition[SparkApplication] { def spec=specification }
  implicit val sparkApplicationListDef = new ResourceDefinition[ListResource[SparkApplication]] { def spec=specification }
}