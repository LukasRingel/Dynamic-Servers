import React from "react";
import LoadingSpinner from "../../../components/LoadingSpinner";
import ErrorPage from "../../../components/500Error";
import {LiveViewLoadingState} from "./data/LiveViewLoadingState";
import {getLiveViewData} from "./data/LiveViewDataSource";
import {notification} from "antd";
import {LiveViewData} from "./data/LiveViewData";
import LiveViewComponent from "./LiveViewComponent";
import LiveViewHeader from "./header/LiveViewHeader";

type ItemListProps = {
  serverId: string
};

const LiveViewContainer: React.FC<ItemListProps> = (props) => {
  const [loadingState, setLoadingState] =
    React.useState(LiveViewLoadingState.LOADING_NOT_STARTED);
  const [liveViewData, setLiveViewData] =
    React.useState<LiveViewData | null>(null);
  const [api, contextHolder] = notification.useNotification();


  if (loadingState === LiveViewLoadingState.LOADING_NOT_STARTED) {
    setLoadingState(LiveViewLoadingState.LOADING);
    getLiveViewData(props.serverId).then((data) => {
      if (!data.ready && data.websocketUrl === "") {
        api.error({
          message: 'Log-Agent not ready',
          description: 'The log-agent on the running server seems to be offline. Please try again later.',
          duration: 5,
        });
        return;
      }

      setLiveViewData(data);
      setLoadingState(LiveViewLoadingState.LOADED_SUCCESS);
    }).catch(() => {
      api.error({
        message: 'Error',
        description: 'An error occurred while trying to load the live view. Please try again later.',
        duration: 5,
      });
      setLoadingState(LiveViewLoadingState.LOADED_ERROR);
    });
    return <>
      {contextHolder}
      <LoadingSpinner size={50}/>
    </>
  }

  if (loadingState === LiveViewLoadingState.LOADING) {
    return <>
      {contextHolder}
      <LoadingSpinner size={50}/>
    </>
  }

  if (loadingState === LiveViewLoadingState.LOADED_ERROR) {
    return <>
      {contextHolder}
      <ErrorPage/>
    </>
  }

  return <>
    <LiveViewHeader serverId={props.serverId}
                    executor={liveViewData?.executor !== null ? liveViewData?.executor!! : "Unknown executor"}
                    startedAt={new Date(liveViewData?.startedAt != null ? liveViewData?.startedAt : 0)}/>
    <LiveViewComponent sessionData={liveViewData}/>
  </>
}

export default LiveViewContainer;