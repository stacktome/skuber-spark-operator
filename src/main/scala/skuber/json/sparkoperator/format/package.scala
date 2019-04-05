package skuber.json.sparkoperator

import skuber._
import play.api.libs.json._
import play.api.libs.functional.syntax._
import skuber.json.format._
import skuber.sparkoperator._

package object format {

  implicit lazy val formatSparkHistoryServerSharedVolume: Format[SparkHistoryServer.SharedVolume] = (
    (JsPath \ "size").formatWithDefault("0.3Gi") and
      (JsPath \ "mountPath").formatWithDefault("/history/spark-events") and
      (JsPath \ "matchLabels").formatMaybeEmptyMap[String]
    )(SparkHistoryServer.SharedVolume.apply _, unlift(SparkHistoryServer.SharedVolume.unapply))

  implicit lazy val formatSparkHistoryServerKerberos: Format[SparkHistoryServer.Kerberos] = (
    (JsPath \ "enabled").formatWithDefault(true) and
      (JsPath \ "principal").formatMaybeEmptyString() and
      (JsPath \ "keytab").formatMaybeEmptyString()
    )(SparkHistoryServer.Kerberos.apply _, unlift(SparkHistoryServer.Kerberos.unapply))

  implicit lazy val formatSparkHistoryServerCleaner: Format[SparkHistoryServer.Cleaner] = (
    (JsPath \ "enabled").formatWithDefault(true) and
      (JsPath \ "interval").formatWithDefault(1) and
      (JsPath \ "maxAge").formatWithDefault(7)
    )(SparkHistoryServer.Cleaner.apply _, unlift(SparkHistoryServer.Cleaner.unapply))

  implicit lazy val formatSparkHistoryServerSpec: Format[SparkHistoryServer.Spec] = (
    (JsPath \ "type").formatEnum(SparkHistoryServer.Type, Some(SparkHistoryServer.Type.sharedVolume)) and
      (JsPath \ "sharedVolume").formatNullable[SparkHistoryServer.SharedVolume] and
      (JsPath \ "sparkConfiguration").formatMaybeEmptyList[EnvVar] and
      (JsPath \ "remoteURI").formatMaybeEmptyString() and
      (JsPath \ "expose").formatWithDefault(true) and
      (JsPath \ "host").formatMaybeEmptyString() and
      (JsPath \ "customImage").formatMaybeEmptyString() and
      (JsPath \ "logDirectory").formatWithDefault("file:/history/spark-events") and
      (JsPath \ "updateInterval").formatWithDefault("10") and
      (JsPath \ "internalPort").formatWithDefault("18080") and
      (JsPath \ "retainedApplications").formatWithDefault("50") and
      (JsPath \ "maxApplications").formatWithDefault("999999") and
      (JsPath \ "provider").formatWithDefault("org.apache.spark.deploy.history.FsHistoryProvider") and
      (JsPath \ "kerberos").formatNullable[SparkHistoryServer.Kerberos] and
      (JsPath \ "cleaner").formatNullable[SparkHistoryServer.Cleaner] and
      (JsPath \ "endEventReparseChunkSize").formatWithDefault(1) and
      (JsPath \ "inProgressOptimization").formatWithDefault(true) and
      (JsPath \ "numReplayThreads").formatMaybeEmptyString() and
      (JsPath \ "maxDiskUsage").formatWithDefault(10) and
      (JsPath \ "persistentPath").formatMaybeEmptyString()
    )(SparkHistoryServer.Spec.apply _, unlift(SparkHistoryServer.Spec.unapply))

  implicit lazy val formatSparkHistoryServer: Format[SparkHistoryServer] = (
    objFormat and
      (JsPath \ "spec").formatNullable[SparkHistoryServer.Spec]
    )(SparkHistoryServer.apply _, unlift(SparkHistoryServer.unapply))

  implicit val formatSparkHistoryServerList: Format[ListResource[SparkHistoryServer]] = ListResourceFormat[SparkHistoryServer]

  implicit lazy val formatSparkClusterRCSpec: Format[SparkCluster.RCSpec] = (
    (JsPath \ "instances").formatWithDefault(1) and
      (JsPath \ "memory").formatMaybeEmptyString() and
      (JsPath \ "cpu").formatMaybeEmptyString() and
      (JsPath \ "labels").formatMaybeEmptyMap[String] and
      (JsPath \ "command").formatMaybeEmptyList[String] and
      (JsPath \ "commandArgs").formatMaybeEmptyList[String]
    )(SparkCluster.RCSpec.apply _, unlift(SparkCluster.RCSpec.unapply))

  implicit lazy val formatSparkClusterHistoryServerSharedVolume: Format[SparkCluster.HistoryServerSharedVolume] = (
    (JsPath \ "size").formatWithDefault("0.3Gi") and
      (JsPath \ "mountPath").formatWithDefault("/history/spark-events") and
      (JsPath \ "matchLabels").formatMaybeEmptyMap[String]
    )(SparkCluster.HistoryServerSharedVolume.apply _, unlift(SparkCluster.HistoryServerSharedVolume.unapply))

  implicit lazy val formatSparkClusterHistoryServer: Format[SparkCluster.HistoryServer] = (
    (JsPath \ "name").formatMaybeEmptyString() and
      (JsPath \ "type").formatEnum(SparkCluster.HistoryServerType, Some(SparkCluster.HistoryServerType.sharedVolume)) and
      (JsPath \ "sharedVolume").formatNullable[SparkCluster.HistoryServerSharedVolume] and
      (JsPath \ "remoteURI").formatMaybeEmptyString()
    )(SparkCluster.HistoryServer.apply _, unlift(SparkCluster.HistoryServer.unapply))

  implicit lazy val formatSparkClusterDownloadData: Format[SparkCluster.DownloadData] = (
    (JsPath \ "url").format[String] and
      (JsPath \ "to").format[String] and
      (JsPath \ "required").formatWithDefault(false) and
      (JsPath \ "downloadTimeout").formatNullable[Int]
    )(SparkCluster.DownloadData.apply _, unlift(SparkCluster.DownloadData.unapply))

  implicit lazy val formatSparkClusterSpec: Format[SparkCluster.Spec] = (
    (JsPath \ "master").formatNullable[SparkCluster.RCSpec] and
      (JsPath \ "worker").formatNullable[SparkCluster.RCSpec] and
      (JsPath \ "customImage").formatMaybeEmptyString() and
      (JsPath \ "metrics").formatWithDefault(false) and
      (JsPath \ "sparkWebUI").formatWithDefault(true) and
      (JsPath \ "sparkConfigurationMap").formatMaybeEmptyString() and
      (JsPath \ "env").formatMaybeEmptyList[EnvVar] and
      (JsPath \ "sparkConfiguration").formatMaybeEmptyList[EnvVar] and
      (JsPath \ "historyServer").formatNullable[SparkCluster.HistoryServer] and
      (JsPath \ "downloadData").formatMaybeEmptyList[SparkCluster.DownloadData]
    )(SparkCluster.Spec.apply _, unlift(SparkCluster.Spec.unapply))

  implicit lazy val formatSparkCluster: Format[SparkCluster] = (
    objFormat and
      (JsPath \ "spec").formatNullable[SparkCluster.Spec]
    )(SparkCluster.apply _, unlift(SparkCluster.unapply))

  implicit val formatSparkClusterList: Format[ListResource[SparkCluster]] = ListResourceFormat[SparkCluster]

  implicit lazy val formatSparkApplicationDeps: Format[SparkApplication.Deps] = (
    (JsPath \ "jars").formatMaybeEmptyList[String] and
      (JsPath \ "files").formatMaybeEmptyList[String] and
      (JsPath \ "pyFiles").formatMaybeEmptyList[String] and
      (JsPath \ "jarsDownloadDir").formatMaybeEmptyList[String] and
      (JsPath \ "filesDownloadDir").formatMaybeEmptyList[String] and
      (JsPath \ "downloadTimeout").formatWithDefault(60) and
      (JsPath \ "maxSimultaneousDownloads").formatWithDefault(5)
    )(SparkApplication.Deps.apply _, unlift(SparkApplication.Deps.unapply))

  implicit lazy val formatSparkApplicationDriverSpec: Format[SparkApplication.DriverSpec] = (
    (JsPath \ "memory").formatWithDefault("512m") and
      (JsPath \ "memoryOverhead").formatMaybeEmptyString() and
      (JsPath \ "labels").formatMaybeEmptyMap[String] and
      (JsPath \ "cores").formatWithDefault("1") and
      (JsPath \ "coreLimit").formatWithDefault("1000m") and
      (JsPath \ "serviceAccount").formatWithDefault("spark-operator")
    )(SparkApplication.DriverSpec.apply _, unlift(SparkApplication.DriverSpec.unapply))

  implicit lazy val formatSparkApplicationExecutorSpec: Format[SparkApplication.ExecutorSpec] = (
    (JsPath \ "memory").formatWithDefault("512m") and
      (JsPath \ "memoryOverhead").formatMaybeEmptyString() and
      (JsPath \ "labels").formatMaybeEmptyMap[String] and
      (JsPath \ "instances").formatWithDefault(1) and
      (JsPath \ "cores").formatWithDefault("1") and
      (JsPath \ "coreLimit").formatWithDefault("1000m")
    )(SparkApplication.ExecutorSpec.apply _, unlift(SparkApplication.ExecutorSpec.unapply))

  implicit lazy val formatVirtualServiceSpec: Format[SparkApplication.Spec] = (
      (JsPath \ "deps").formatNullable[SparkApplication.Deps] and
        (JsPath \ "historyServer").formatMaybeEmptyString() and
        (JsPath \ "driver").formatNullable[SparkApplication.DriverSpec] and
        (JsPath \ "executor").formatNullable[SparkApplication.ExecutorSpec] and
        (JsPath \ "image").formatMaybeEmptyString() and
        (JsPath \ "mainApplicationFile").format[String] and
        (JsPath \ "mainClass").formatMaybeEmptyString() and
        (JsPath \ "arguments").formatMaybeEmptyString() and
        (JsPath \ "mode").formatEnum(SparkApplication.Mode, Some(SparkApplication.Mode.cluster)) and
        (JsPath \ "restartPolicy").formatEnum(RestartPolicy, Some(RestartPolicy.Always)) and
        (JsPath \ "imagePullPolicy").formatEnum(Container.PullPolicy, Some(Container.PullPolicy.IfNotPresent)) and
        (JsPath \ "type").formatEnum(SparkApplication.Type, Some(SparkApplication.Type.Java)) and
        (JsPath \ "sleep").formatWithDefault(31536000) and
        (JsPath \ "env").formatMaybeEmptyList[EnvVar] and
        (JsPath \ "sparkConfigMap").formatMaybeEmptyString()
    )(SparkApplication.Spec.apply _, unlift(SparkApplication.Spec.unapply))

  implicit lazy val formatSparkApplication: Format[SparkApplication] = (
    objFormat and
      (JsPath \ "spec").formatNullable[SparkApplication.Spec]
    )(SparkApplication.apply _, unlift(SparkApplication.unapply))

  implicit val formatSparkApplicationList: Format[ListResource[SparkApplication]] = ListResourceFormat[SparkApplication]
}
