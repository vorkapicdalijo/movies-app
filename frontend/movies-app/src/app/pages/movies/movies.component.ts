import { Component, OnDestroy, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { Subscription } from 'rxjs';
import { MovieDetailsDialogComponent } from 'src/app/components/dialogs/movie-details-dialog/movie-details-dialog.component';
import { MoviePagination } from 'src/app/models/movie-pagination.model';
import { Movie } from 'src/app/models/movie.model';
import { MoviesService } from 'src/app/services/movies.service';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.scss']
})
export class MoviesComponent implements OnInit, OnDestroy {

  private subscriptions: Subscription[] = [];

  public defaultPagination: MoviePagination = {
    offset: 0,
    limit: 10
  };

  public movies: Movie[];
  public moviesPagination: MoviePagination;

  public isLoading: boolean = true;

  constructor(
    private moviesService: MoviesService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    let loadSub = this.moviesService.moviesLoad$
      .subscribe((paginationDto: MoviePagination) => {
        this.moviesPagination = paginationDto;
        this.movies = paginationDto.data;

        this.isLoading = false;
      });

    this.loadMovies();

    this.subscriptions.push(loadSub);
  }

  public openMovieDetailsDialog(movieId: number) {
    const dialogRef = this.dialog.open(MovieDetailsDialogComponent,
      {
        data: {
          movieId: movieId
        }
      });

    dialogRef.afterClosed()
      .subscribe(res => {

      });
  }

  private loadMovies() {
      this.moviesService.getMoviesByPagination(this.defaultPagination);
  }

  ngOnDestroy(): void {
    this.moviesService.ngOnDestroy();
    this.subscriptions.forEach(sub => {
      sub.unsubscribe();
    });
  }
}
