import React, {useEffect} from "react";
import {RunningServersLoadingState} from "./RunningServersLoadingState";
import LoadingSpinner from "../../../components/LoadingSpinner";
import ErrorPage from "../../../components/500Error";
import RunningServersList from "./RunningServersList";
import {getAllRunningServers} from "./RunningServersDataSource";
import {RunningServer} from "./model/RunningServer";

type ItemListProps = {};

const RunningServersContainer: React.FC<ItemListProps> = () => {
  const [loadingState, setLoadingState] =
    React.useState<RunningServersLoadingState>(RunningServersLoadingState.LOADING_NOT_STARTED);
  const [inBackground, setInBackground] =
    React.useState<boolean>(false);
  const [runningServers, setRunningServers] =
    React.useState<RunningServer[]>([]);

  useEffect(() => {
    loadRunningServers()
    setInterval(() => {
      loadRunningServers()
    }, 1000);
  }, []);

  function loadRunningServers() {
    getAllRunningServers().then((runningServers) => {
      setRunningServers(runningServers);
      setInBackground(true)
      setLoadingState(RunningServersLoadingState.LOADED_SUCCESS);
    }).catch(() => {
      setLoadingState(RunningServersLoadingState.LOADED_ERROR);
    })
  }

  if (loadingState === RunningServersLoadingState.LOADING_NOT_STARTED && !inBackground) {
    setLoadingState(RunningServersLoadingState.LOADING);
    loadRunningServers();
    return <LoadingSpinner size={50}/>
  }

  if (loadingState === RunningServersLoadingState.LOADING && !inBackground) {
    return <LoadingSpinner size={50}/>
  }

  if (loadingState === RunningServersLoadingState.LOADED_ERROR && !inBackground) {
    return <ErrorPage/>
  }

  return <RunningServersList runningServers={runningServers}/>
}

export default RunningServersContainer;