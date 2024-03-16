import {ServerStatus} from "./ServerStatus";

export class RunningServer {
  id: string;
  template: string;
  executor: string;
  status: ServerStatus;
  createdAt: Date;
  tags: string[];

  constructor(id: string, template: string, executor: string, status: ServerStatus, createdAt: Date, tags: string[]) {
    this.id = id;
    this.template = template;
    this.executor = executor;
    this.status = status;
    this.createdAt = createdAt;
    this.tags = tags;
  }
}