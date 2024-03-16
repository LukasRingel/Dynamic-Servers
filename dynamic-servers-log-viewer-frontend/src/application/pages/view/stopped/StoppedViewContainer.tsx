import React from "react";
import {StoppedViewLoadingState} from "./data/StoppedViewLoadingState";
import LoadingSpinner from "../../../components/LoadingSpinner";
import ErrorPage from "../../../components/500Error";
import {StoppedLog} from "./data/StoppedLog";
import {getStoppedView} from "./data/StoppedLogDataSource";
import StoppedViewComponent from "./StoppedViewComponent";
import StoppedViewHeader from "./header/StoppedViewHeader";

type ItemListProps = {
  serverId: string
};

const StoppedViewContainer: React.FC<ItemListProps> = (props) => {
  const [loadingState, setLoadingState] =
    React.useState(StoppedViewLoadingState.LOADING_NOT_STARTED);
  const [log, setLog] =
    React.useState<StoppedLog | null>(null);

  function loadLog() {
    getStoppedView(props.serverId).then((log) => {
      setLog(log);
      setLoadingState(StoppedViewLoadingState.LOADED_SUCCESS);
    }).catch(() => {
      setLoadingState(StoppedViewLoadingState.LOADED_ERROR);
    })
  }

  if (loadingState === StoppedViewLoadingState.LOADING_NOT_STARTED) {
    setLoadingState(StoppedViewLoadingState.LOADING);
    loadLog()
    return <LoadingSpinner size={50}/>
  }

  if (loadingState === StoppedViewLoadingState.LOADING) {
    return <LoadingSpinner size={50}/>
  }

  if (loadingState === StoppedViewLoadingState.LOADED_ERROR) {
    return <ErrorPage/>
  }

  return (
    <>
      <StoppedViewHeader serverId={props.serverId}/>
      <StoppedViewComponent lines={log!!.lines}/>
    </>
  );
}

export default StoppedViewContainer;