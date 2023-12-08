import { Movie } from './movie.model';

export interface MoviePagination {
    offset: number,
    limit: number,
    totalLength?: number,

    //Filters
    getTop10MoviesByRevenue?: boolean,
    getTop10MoviesByRevenueByYear?: boolean,
    filterYear?: string,
    data?: Movie[]
}
