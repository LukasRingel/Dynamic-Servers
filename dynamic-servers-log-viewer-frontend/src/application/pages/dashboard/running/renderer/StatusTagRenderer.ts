import {ServerStatus} from "../model/ServerStatus";

export const colorMap = {
  [ServerStatus.STARTING]: "#9ebeff",
  [ServerStatus.RUNNING]: "#1ea800",
  [ServerStatus.STOPPING]: "#ff9e00",
  [ServerStatus.STOPPED]: "#ff0000",
  [ServerStatus.SAVING_LOG]: "#ff0000",
  [ServerStatus.SAVING_LOG_FAILED]: "#ff0000",
  [ServerStatus.SAVING_LOG_SUCCESS]: "#ff0000",
  [ServerStatus.READY_TO_DELETE]: "#ff0000",
  [ServerStatus.DELETED]: "#ff0000"
}

export const textMap = {
  [ServerStatus.STARTING]: "Starting",
  [ServerStatus.RUNNING]: "Running",
  [ServerStatus.STOPPING]: "Stopping",
  [ServerStatus.STOPPED]: "Stopped",
  [ServerStatus.SAVING_LOG]: "Saving log",
  [ServerStatus.SAVING_LOG_FAILED]: "Saving log failed",
  [ServerStatus.SAVING_LOG_SUCCESS]: "Saving log success",
  [ServerStatus.READY_TO_DELETE]: "Ready to delete",
  [ServerStatus.DELETED]: "Deleted"
}