import axios from "axios";
import {LiveViewData} from "./LiveViewData";

const url = "http://localhost:8086/api/view/live"

export async function getLiveViewData(serverId: string): Promise<LiveViewData> {
  let data = await axios.get(url, {
    params: {
      serverId: serverId
    }
  });
  return data.data;
}
