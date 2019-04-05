package skuber.sparkoperator

import skuber.ResourceSpecification.{Names, Scope}
import skuber._

case class SparkApplication(
                             kind: String = "SparkApplication",
                             override val apiVersion: String = "sparkoperator.k8s.io/v1beta1",
                             metadata: ObjectMeta = ObjectMeta(),
                             spec: Option[SparkApplication.Spec] = None
                           ) extends ObjectResource

object SparkApplication {
  object Mode extends Enumeration {
    type Mode = Value
    val client, cluster = Value
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
                 downloadTimeout: Int = 60,
                 maxSimultaneousDownloads: Int = 5
                 )

  case class DriverSpec(
                       memory: String = "512m",
                       memoryOverhead: String = "",
                       //                        image: String = "",
                       labels: Map[String,String] = Map(),
                       cores: String = "0.5",
                       coreLimit: String = "500m",
                       serviceAccount: String = "spark-operator"
                       )

  case class ExecutorSpec(
                         memory: String = "512m",
                         memoryOverhead: String = "",
                         //                         image: String = "",
                         labels: Map[String,String] = Map(),
                         instances: Int = 1,
                         cores: String = "1",
                         coreLimit: String = "1000m"
                         )

  case class Spec(
                 deps: Option[SparkApplication.Deps] = None,
                 historyServer: String = "",
                 driver: Option[SparkApplication.DriverSpec] = None,
                 executor: Option[SparkApplication.ExecutorSpec] = None,
                 image: String = "",
                 mainApplicationFile: String,
                 mainClass: String = "",
                 arguments: String = "",
                 mode: SparkApplication.Mode.Value = SparkApplication.Mode.cluster,
                 restartPolicy: RestartPolicy.Value = RestartPolicy.Always,
                 imagePullPolicy: Container.PullPolicy.Value = Container.PullPolicy.IfNotPresent,
                 `type`: SparkApplication.Type.Value = SparkApplication.Type.Java,
                 sleep: Int = 31536000,
                 env: List[EnvVar] = List(),
                 sparkConfigMap: String = ""
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