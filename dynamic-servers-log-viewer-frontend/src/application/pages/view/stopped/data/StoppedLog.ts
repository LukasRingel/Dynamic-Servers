export class StoppedLog {
  doNotDeleteBefore: string;
  deletedAt: string;
  lines: string;

  constructor(doNotDeleteBefore: string, deletedAt: string, lines: string) {
    this.doNotDeleteBefore = doNotDeleteBefore;
    this.deletedAt = deletedAt;
    this.lines = lines;
  }
}