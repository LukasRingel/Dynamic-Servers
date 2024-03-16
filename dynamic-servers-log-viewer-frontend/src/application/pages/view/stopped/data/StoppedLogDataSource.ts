import {StoppedLog} from "./StoppedLog";
import axios from "axios";
import {ServerHistory} from "./ServerHistory";

const url = "http://localhost:8086/api/view/stopped"

export async function getStoppedView(serverId: string): Promise<StoppedLog> {
  let data = await axios.get(url, {
    params: {
      serverId: serverId
    }
  });
  return data.data;
}

export async function getServerEvents(serverId: string): Promise<ServerHistory> {
  let data = await axios.get(url + "/events", {
    params: {
      serverId: serverId
    }
  });
  return data.data;
}