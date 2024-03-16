import React from "react";
import {useParams} from "react-router-dom";
import StoppedViewContainer from "./stopped/StoppedViewContainer";
import NotFoundPage from "../../components/404NotFound";
import LiveViewContainer from "./live/LiveViewContainer";

type ItemListProps = {
};

const ViewContainer: React.FC<ItemListProps> = (props) => {
  const {mode, serverId} = useParams();

  if (mode === "stopped" && serverId !== undefined) {
    return <StoppedViewContainer serverId={serverId}/>
  }

  if (mode === "live" && serverId !== undefined) {
    return <LiveViewContainer serverId={serverId}/>
  }

  return <NotFoundPage/>
}

export default ViewContainer;