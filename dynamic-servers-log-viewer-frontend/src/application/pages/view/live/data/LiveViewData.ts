export class LiveViewData {
  session: string;
  serverId: string;
  ready: boolean;
  executor: string;
  startedAt: number;
  websocketUrl: string;

  constructor(session: string, serverId: string, ready: boolean, executor: string, startedAt: number, websocketUrl: string) {
    this.session = session;
    this.serverId = serverId;
    this.ready = ready;
    this.executor = executor;
    this.startedAt = startedAt;
    this.websocketUrl = websocketUrl;
  }
}