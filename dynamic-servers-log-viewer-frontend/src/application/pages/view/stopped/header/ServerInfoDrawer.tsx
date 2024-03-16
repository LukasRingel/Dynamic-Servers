import React from "react";
import {Drawer} from "antd";
import ServerInfoEventsContainer from "./ServerInfoEventsContainer";

type ItemListProps = {
  serverInfoVisible: boolean,
  close: () => void,
  serverId: string
};

const ServerInfoDrawer: React.FC<ItemListProps> = (props) => {
  return <Drawer title={"Server Info"} open={props.serverInfoVisible} onClose={props.close}>
    <p>Server ID: {props.serverId}</p>
    <ServerInfoEventsContainer serverId={props.serverId}/>
  </Drawer>
}

export default ServerInfoDrawer;