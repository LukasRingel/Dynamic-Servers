export class DashboardStatistics {
  todayLogs: number;
  lastThirtyDaysLogs: number;
  totalLogs: number;
  deleteLogsAfterDays: number;

  constructor(todayLogs: number, lastThirtyDaysLogs: number, totalLogs: number, deleteLogsAfterDays: number) {
    this.todayLogs = todayLogs;
    this.lastThirtyDaysLogs = lastThirtyDaysLogs;
    this.totalLogs = totalLogs;
    this.deleteLogsAfterDays = deleteLogsAfterDays;
  }
}