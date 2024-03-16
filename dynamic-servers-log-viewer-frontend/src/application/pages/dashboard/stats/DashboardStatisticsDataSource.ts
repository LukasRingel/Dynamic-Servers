import axios from "axios";
import {DashboardStatistics} from "./DashboardStatistics";

const url = "http://localhost:8086/api/home/stats"

export async function getStatistics(): Promise<DashboardStatistics> {
  let data = await axios.get(url);
  return data.data;
}