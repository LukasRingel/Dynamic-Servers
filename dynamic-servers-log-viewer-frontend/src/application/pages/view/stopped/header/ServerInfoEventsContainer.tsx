import React from "react";
import {StoppedViewLoadingState} from "../data/StoppedViewLoadingState";
import LoadingSpinner from "../../../../components/LoadingSpinner";
import {getServerEvents} from "../data/StoppedLogDataSource";
import ErrorPage from "../../../../components/500Error";
import {Steps} from "antd";
import {ServerHistory} from "../data/ServerHistory";
import {renderEventType} from "../data/renderer/EventTypeRenderer";

type ItemListProps = {
  serverId: string
};

const ServerInfoEventsContainer: React.FC<ItemListProps> = ({serverId}) => {
  const [loadingState, setLoadingState] =
    React.useState(StoppedViewLoadingState.LOADING_NOT_STARTED);
  const [history, setHistory] =
    React.useState<ServerHistory | null>(null);

  if (loadingState === StoppedViewLoadingState.LOADING_NOT_STARTED) {
    getServerEvents(serverId).then((events) => {
      setHistory(events);
      setLoadingState(StoppedViewLoadingState.LOADED_SUCCESS);
    }).catch(() => {
      setLoadingState(StoppedViewLoadingState.LOADED_ERROR);
    });
    setLoadingState(StoppedViewLoadingState.LOADING);
    return <LoadingSpinner size={25}/>
  }

  if (loadingState === StoppedViewLoadingState.LOADING) {
    return <LoadingSpinner size={25}/>
  }

  if (loadingState === StoppedViewLoadingState.LOADED_ERROR) {
    return <ErrorPage/>
  }

  return <>
    <p>Executor: {history?.executorId}</p>
    <p>Template: {history?.template}</p>
    <p>Created: {new Date(history?.createdAt != null ? history.createdAt : 0).toLocaleString()}</p>
    <hr/>
    <h4>Updates:</h4>
    <Steps
      current={history?.updates.length}
      direction="vertical"
      items={history?.updates.map(renderEventType)}
    /></>
}

export default ServerInfoEventsContainer;