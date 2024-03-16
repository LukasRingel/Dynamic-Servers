import axios from "axios";
import {FoundEntity} from "../FoundEntity";

const url = "http://localhost:8086/api/search"

export async function getServersWithFilter(template: String, executor: String, before: Date, after: Date, limit: number): Promise<FoundEntity[]> {
  let data = await axios.get(url, {
    params: {
      template: template,
      executor: executor,
      before: before.getTime(),
      after: after.getTime(),
      limit: limit
    }
  });
  return data.data;
}