import React, {useState} from "react";
import {DashboardStatisticsLoadingState} from "./DashboardStatisticsLoadingState";
import {DashboardStatistics} from "./DashboardStatistics";
import LoadingSpinner from "../../../components/LoadingSpinner";
import ErrorPage from "../../../components/500Error";
import {getStatistics} from "./DashboardStatisticsDataSource";
import StatisticsComponent from "./StatisticsComponent";

type ItemListProps = {};

const StatisticsContainer: React.FC<ItemListProps> = (props) => {
  const [loadingState, setLoadingState] =
    useState<DashboardStatisticsLoadingState>(DashboardStatisticsLoadingState.LOADING_NOT_STARTED)
  const [statistics, setStatistics] =
    useState<DashboardStatistics | null>(null)

  if (loadingState === DashboardStatisticsLoadingState.LOADING_NOT_STARTED) {
    getStatistics().then(value => {
      setStatistics(value);
      setLoadingState(DashboardStatisticsLoadingState.LOADED_SUCCESS);
    }).catch(() => {
      setLoadingState(DashboardStatisticsLoadingState.LOADED_ERROR);
    });
    setLoadingState(DashboardStatisticsLoadingState.LOADING);
    return <></>
  }

  if (loadingState === DashboardStatisticsLoadingState.LOADING) {
    return <LoadingSpinner size={50}/>
  }

  if (loadingState === DashboardStatisticsLoadingState.LOADED_ERROR) {
    return <ErrorPage/>
  }

  return <StatisticsComponent stats={statistics!!} />
}

export default StatisticsContainer;