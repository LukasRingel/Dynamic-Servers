import {ServerEvent} from "./ServerEvent";

export class ServerHistory {
  template: string;
  executorId: string;
  createdAt: number;
  updates: ServerEvent[];

  constructor(template: string, executorId: string, createdAt: number, updates: ServerEvent[]) {
    this.template = template;
    this.executorId = executorId;
    this.createdAt = createdAt;
    this.updates = updates;
  }
}