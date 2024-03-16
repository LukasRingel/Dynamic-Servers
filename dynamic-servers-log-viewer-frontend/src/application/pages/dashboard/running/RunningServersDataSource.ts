import {RunningServer} from "./model/RunningServer";
import axios from "axios";

const url = "http://localhost:8086/api/home/running"

export async function getAllRunningServers(): Promise<RunningServer[]> {
  let data = await axios.get(url);
  return data.data;
}