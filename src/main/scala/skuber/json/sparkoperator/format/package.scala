package skuber.json.sparkoperator

import skuber._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import skuber.json.format._
import skuber.sparkoperator._

package object format {

  implicit lazy val formatSparkApplicationDeps: Format[SparkApplication.Deps] = (
    (JsPath \ "jars").formatMaybeEmptyList[String] and
      (JsPath \ "files").formatMaybeEmptyList[String] and
      (JsPath \ "pyFiles").formatMaybeEmptyList[String] and
      (JsPath \ "jarsDownloadDir").formatMaybeEmptyList[String] and
      (JsPath \ "filesDownloadDir").formatMaybeEmptyList[String] and
      (JsPath \ "downloadTimeout").formatNullable[Int] and
      (JsPath \ "maxSimultaneousDownloads").formatNullable[Int]
    )(SparkApplication.Deps.apply _, unlift(SparkApplication.Deps.unapply))

  implicit lazy val formatNamePath: Format[SparkApplication.NamePath] = (
    (JsPath \ "name").format[String] and
      (JsPath \ "path").format[String]
    )(SparkApplication.NamePath.apply _, unlift(SparkApplication.NamePath.unapply))

  implicit lazy val formatNameKey: Format[SparkApplication.NameKey] = (
    (JsPath \ "name").format[String] and
      (JsPath \ "key").format[String]
    )(SparkApplication.NameKey.apply _, unlift(SparkApplication.NameKey.unapply))

  implicit lazy val formatSecretInfo: Format[SparkApplication.SecretInfo] = (
    (JsPath \ "name").format[String] and
      (JsPath \ "path").format[String] and
      (JsPath \ "secretType").format[String]
    )(SparkApplication.SecretInfo.apply _, unlift(SparkApplication.SecretInfo.unapply))

  implicit lazy val formatSparkApplicationExecutorSpec: Format[SparkApplication.ExecutorSpec] = (
    (JsPath \ "cores").formatNullable[Float] and
      (JsPath \ "coreLimit").formatMaybeEmptyString() and
      (JsPath \ "memory").formatMaybeEmptyString() and
      (JsPath \ "memoryOverhead").formatMaybeEmptyString() and
      (JsPath \ "image").formatMaybeEmptyString() and
      (JsPath \ "configMaps").formatMaybeEmptyList[SparkApplication.NamePath] and
      (JsPath \ "secrets").formatMaybeEmptyList[SparkApplication.SecretInfo] and
      (JsPath \ "envVars").formatMaybeEmptyMap[String] and
      (JsPath \ "envSecretKeyRefs").formatMaybeEmptyMap[SparkApplication.NameKey] and
      (JsPath \ "labels").formatMaybeEmptyMap[String] and
      (JsPath \ "annotations").formatMaybeEmptyMap[String] and
      (JsPath \ "volumeMounts").formatMaybeEmptyList[Volume.Mount] and
      (JsPath \ "affinity").formatNullable[Pod.Affinity] and
      (JsPath \ "tolerations").formatMaybeEmptyList[Pod.Toleration] and
      (JsPath \ "securityContext").formatNullable[PodSecurityContext] and
      (JsPath \ "schedulerName").formatMaybeEmptyString() and
      (JsPath \ "instances").formatNullable[Int] and
      (JsPath \ "coreRequest").formatMaybeEmptyString() and
      (JsPath \ "javaOptions").formatMaybeEmptyString()
    )(SparkApplication.ExecutorSpec.apply _, unlift(SparkApplication.ExecutorSpec.unapply))

  implicit lazy val formatSparkApplicationDriverSpec: Format[SparkApplication.DriverSpec] = (
    (JsPath \ "cores").formatNullable[Float] and
      (JsPath \ "coreLimit").formatMaybeEmptyString() and
      (JsPath \ "memory").formatMaybeEmptyString() and
      (JsPath \ "memoryOverhead").formatMaybeEmptyString() and
      (JsPath \ "image").formatMaybeEmptyString() and
      (JsPath \ "configMaps").formatMaybeEmptyList[SparkApplication.NamePath] and
      (JsPath \ "secrets").formatMaybeEmptyList[SparkApplication.SecretInfo] and
      (JsPath \ "envVars").formatMaybeEmptyMap[String] and
      (JsPath \ "envSecretKeyRefs").formatMaybeEmptyMap[SparkApplication.NameKey] and
      (JsPath \ "labels").formatMaybeEmptyMap[String] and
      (JsPath \ "annotations").formatMaybeEmptyMap[String] and
      (JsPath \ "volumeMounts").formatMaybeEmptyList[Volume.Mount] and
      (JsPath \ "affinity").formatNullable[Pod.Affinity] and
      (JsPath \ "tolerations").formatMaybeEmptyList[Pod.Toleration] and
      (JsPath \ "securityContext").formatNullable[PodSecurityContext] and
      (JsPath \ "schedulerName").formatMaybeEmptyString() and
      (JsPath \ "podName").formatMaybeEmptyString() and
      (JsPath \ "serviceAccount").formatMaybeEmptyString() and
      (JsPath \ "javaOptions").formatMaybeEmptyString()
    )(SparkApplication.DriverSpec.apply _, unlift(SparkApplication.DriverSpec.unapply))

  implicit lazy val formatPrometheusSpec: Format[SparkApplication.PrometheusSpec] = (
    (JsPath \ "jmxExporterJar").format[String] and
      (JsPath \ "port").format[Int] and
      (JsPath \ "configFile").formatMaybeEmptyString() and
      (JsPath \ "configuration").formatMaybeEmptyString()
    )(SparkApplication.PrometheusSpec.apply _, unlift(SparkApplication.PrometheusSpec.unapply))

  implicit lazy val formatMonitoringSpec: Format[SparkApplication.MonitoringSpec] = (
    (JsPath \ "exposeDriverMetrics").format[Boolean] and
      (JsPath \ "exposeExecutorMetrics").format[Boolean] and
      (JsPath \ "metricsProperties").formatMaybeEmptyString() and
      (JsPath \ "prometheus").formatNullable[SparkApplication.PrometheusSpec]
    )(SparkApplication.MonitoringSpec.apply _, unlift(SparkApplication.MonitoringSpec.unapply))

  implicit lazy val formatRestartPolicy: Format[SparkApplication.RestartPolicy] = (
    (JsPath \ "type").formatNullableEnum(skuber.RestartPolicy) and
      (JsPath \ "onSubmissionFailureRetries").formatNullable[Int] and
      (JsPath \ "onFailureRetries").formatNullable[Int] and
      (JsPath \ "onSubmissionFailureRetryInterval").formatNullable[Int] and
      (JsPath \ "onFailureRetryInterval").formatNullable[Int]
    )(SparkApplication.RestartPolicy.apply _, unlift(SparkApplication.RestartPolicy.unapply))

  val formatSparkApplicationSpec1: OFormat[(SparkApplication.Type.Value, String, Option[SparkApplication.Mode.Value], String,
    String, Option[Container.PullPolicy.Value], List[String], String, String, List[String], Map[String,String], Map[String,String],
    String, String, List[Volume], Option[SparkApplication.DriverSpec], Option[SparkApplication.ExecutorSpec],
    Option[SparkApplication.Deps], Option[SparkApplication.RestartPolicy], Map[String,String], Option[Int])] = (
    (JsPath \ "type").formatEnum(SparkApplication.Type) and
      (JsPath \ "sparkVersion").formatMaybeEmptyString() and
      (JsPath \ "mode").formatNullableEnum(SparkApplication.Mode) and
      (JsPath \ "image").formatMaybeEmptyString() and
      (JsPath \ "initContainerImage").formatMaybeEmptyString() and
      (JsPath \ "imagePullPolicy").formatNullableEnum(Container.PullPolicy) and
      (JsPath \ "imagePullSecrets").formatMaybeEmptyList[String] and
      (JsPath \ "mainClass").formatMaybeEmptyString() and
      (JsPath \ "mainApplicationFile").formatMaybeEmptyString() and
      (JsPath \ "arguments").formatMaybeEmptyList[String] and
      (JsPath \ "sparkConf").formatMaybeEmptyMap[String] and
      (JsPath \ "hadoopConf").formatMaybeEmptyMap[String] and
      (JsPath \ "sparkConfigMap").formatMaybeEmptyString() and
      (JsPath \ "hadoopConfigMap").formatMaybeEmptyString() and
      (JsPath \ "volumes").formatMaybeEmptyList[Volume] and
      (JsPath \ "driver").formatNullable[SparkApplication.DriverSpec] and
      (JsPath \ "executor").formatNullable[SparkApplication.ExecutorSpec] and
      (JsPath \ "deps").formatNullable[SparkApplication.Deps] and
      (JsPath \ "restartPolicy").formatNullable[SparkApplication.RestartPolicy] and
      (JsPath \ "nodeSelector").formatMaybeEmptyMap[String] and
      (JsPath \ "failureRetries").formatNullable[Int]
  ).tupled

  val formatSparkApplicationSpec2: OFormat[(Option[Int], String, String, Option[SparkApplication.MonitoringSpec])] = (
    (JsPath \ "retryInterval").formatNullable[Int] and
      (JsPath \ "pythonVersion").formatMaybeEmptyString() and
      (JsPath \ "memoryOverheadFactor").formatMaybeEmptyString() and
      (JsPath \ "monitoring").formatNullable[SparkApplication.MonitoringSpec]
  ).tupled

  implicit val formatSparkApplicationSpec: Format[SparkApplication.Spec] = (
    formatSparkApplicationSpec1 and formatSparkApplicationSpec2).apply(
    {
      case (one, two) => SparkApplication.Spec(one._1, one._2, one._3, one._4, one._5, one._6, one._7, one._8, one._9,
        one._10, one._11, one._12, one._13, one._14, one._15, one._16, one._17, one._18, one._19, one._20, one._21, two._1,
        two._2, two._3, two._4)
    },
    s => (
      (
        s.`type`,
        s.sparkVersion,
        s.mode,
        s.image,
        s.initContainerImage,
        s.imagePullPolicy,
        s.imagePullSecrets,
        s.mainClass,
        s.mainApplicationFile,
        s.arguments,
        s.sparkConf,
        s.hadoopConf,
        s.sparkConfigMap,
        s.hadoopConfigMap,
        s.volumes,
        s.driver,
        s.executor,
        s.deps,
        s.restartPolicy,
        s.nodeSelector,
        s.failureRetries
      ),
      (
        s.retryInterval,
        s.pythonVersion,
        s.memoryOverheadFactor,
        s.monitoring
      )
    )
  )

  implicit lazy val formatSparkApplicationDriverInfo: Format[SparkApplication.DriverInfo] = (
    (JsPath \ "webUIServiceName").formatMaybeEmptyString() and
      (JsPath \ "webUIPort").formatNullable[Int] and
      (JsPath \ "webUIAddress").formatMaybeEmptyString() and
      (JsPath \ "webUIIngressName").formatMaybeEmptyString() and
      (JsPath \ "webUIIngressAddress").formatMaybeEmptyString() and
      (JsPath \ "podName").formatMaybeEmptyString()
    )(SparkApplication.DriverInfo.apply _, unlift(SparkApplication.DriverInfo.unapply))

  implicit lazy val formatSparkApplicationApplicationState: Format[SparkApplication.ApplicationState] = (
    (JsPath \ "state").formatMaybeEmptyString() and
      (JsPath \ "errorMessage").formatMaybeEmptyString()
    )(SparkApplication.ApplicationState.apply _, unlift(SparkApplication.ApplicationState.unapply))

  implicit lazy val formatSparkApplicationStatus: Format[SparkApplication.Status] = (
    (JsPath \ "sparkApplicationId").formatMaybeEmptyString() and
      (JsPath \ "submissionID").formatMaybeEmptyString() and
      (JsPath \ "lastSubmissionAttemptTime").formatMaybeEmptyString() and
      (JsPath \ "terminationTime").formatMaybeEmptyString() and
      (JsPath \ "driverInfo").format[SparkApplication.DriverInfo] and
      (JsPath \ "applicationState").formatNullable[SparkApplication.ApplicationState] and
      (JsPath \ "executorState").formatMaybeEmptyMap[String] and
      (JsPath \ "executionAttempts").formatNullable[Int] and
      (JsPath \ "submissionAttempts").formatNullable[Int]
    )(SparkApplication.Status.apply _, unlift(SparkApplication.Status.unapply))

  implicit lazy val formatSparkApplication: Format[SparkApplication] = (
    objFormat and
      (JsPath \ "spec").formatNullable[SparkApplication.Spec] and
      (JsPath \ "status").formatNullable[SparkApplication.Status]
    )(SparkApplication.apply _, unlift(SparkApplication.unapply))

  implicit val formatSparkApplicationList: Format[ListResource[SparkApplication]] = ListResourceFormat[SparkApplication]
}
