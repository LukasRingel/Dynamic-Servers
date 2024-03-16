export class FoundEntity {
  serverId: string;
  executorId: string;
  template: string;
  createdAt: number;
  stoppedAt: number;

  constructor(serverId: string, executorId: string, template: string, createdAt: number, stoppedAt: number) {
    this.serverId = serverId;
    this.executorId = executorId;
    this.template = template;
    this.createdAt = createdAt;
    this.stoppedAt = stoppedAt;
  }
}