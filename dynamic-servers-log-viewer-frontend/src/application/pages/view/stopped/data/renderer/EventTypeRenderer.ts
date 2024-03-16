import {StepProps} from "antd";
import {KeyValue, ServerEvent} from "../ServerEvent";

export function renderEventType(event: ServerEvent): StepProps {
  return {
    title: writeTitle(event.type, event.metaData),
    description: new Date(event.timestamp).toLocaleString()
  }
}

function writeTitle(event: string, metaData: KeyValue): string {
  switch (event) {
    case "STATUS_UPDATE_CREATED":
      return "Container created";
    case "STATUS_UPDATE_STARTING_TO_RUNNING":
      return "Container running";
    case "STATUS_UPDATE_RUNNING_TO_STOPPING":
      return "Container stopping";
    case "STATUS_UPDATE_REMOVED":
      return "Container removed";
    case "STATUS_UPDATE_STOPPING_TO_STOPPED":
      return "Container stopped";
    case "STATUS_UPDATE_STOPPED_TO_SAVING_LOG":
      return "Saving container log";
    case "STATUS_UPDATE_SAVING_LOG_TO_SAVING_LOG_FAILED":
      return "Log saving failed";
    case "STATUS_UPDATE_SAVING_LOG_TO_SAVING_LOG_SUCCESS":
      return "Log saved";
    case "STATUS_UPDATE_TO_READY_TO_DELETE":
      return "Ready to delete";
    case "STATUS_UPDATE_READY_TO_DELETE_TO_DELETED":
      return "Container deleted";
    case "TAGS_ELEMENT_ADDED":
      return "Tag added (" + metaData.tag + ")";
    default:
      return event;
  }
}