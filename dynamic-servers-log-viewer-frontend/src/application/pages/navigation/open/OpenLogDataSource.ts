import axios from "axios";
import {OpenLogResponse} from "./OpenLogResponse";

const url = "http://localhost:8086/api/view/decide"

export async function decideIfLiveOfStopped(serverId: string): Promise<OpenLogResponse> {
  let data = await axios.get(url, {
    params: {
      serverId: serverId
    }
  });
  return data.data;
}