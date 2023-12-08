import { Injectable, OnDestroy } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { BehaviorSubject, Observable, Subject, Subscription } from 'rxjs';
import { Movie } from '../models/movie.model';
import { environment } from 'src/environments/environment';
import { MoviePagination } from '../models/movie-pagination.model';
import { MovieFilter } from '../models/movie-filter.model';
@Injectable({
  providedIn: 'root'
})
export class MoviesService implements OnDestroy {

  private filters$ = new BehaviorSubject<MovieFilter | null>(null);

  public moviesLoad$: Subject<MoviePagination> = new Subject<MoviePagination>();

  private serviceSubscriptions!: Subscription[];

  constructor(
    private http: HttpClient
  ) {
  }

  public getMoviesByPagination(pagination: MoviePagination): void {
    let getSub = this.http.post<MoviePagination>(environment.baseUrl + environment.movies,
      {
        ...pagination,
        ...this.filters$.getValue()
      })
      .subscribe((pagination: MoviePagination) => {
        this.moviesLoad$.next(pagination);
      })

    this.serviceSubscriptions.push(getSub);
  }

  public setFilters(filters: MovieFilter | null) {
    this.filters$.next(filters);
  }


  public getMovieDetailsByMovieId(movieId: number): Observable<Movie> {
    return this.http.get<Movie>(environment.baseUrl + environment.movies + `/${movieId}`);
  }

  ngOnDestroy(): void {
      this.serviceSubscriptions.forEach(sub =>
        {
          sub.unsubscribe();
        }
      );
  }
}
