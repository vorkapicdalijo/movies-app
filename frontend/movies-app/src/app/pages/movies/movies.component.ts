import { Component, OnInit } from '@angular/core';
import { MatDialog } from '@angular/material/dialog';
import { MovieDetailsDialogComponent } from 'src/app/components/dialogs/movie-details-dialog/movie-details-dialog.component';
import { Movie } from 'src/app/models/movie.model';
import { MoviesService } from 'src/app/services/movies.service';

@Component({
  selector: 'app-movies',
  templateUrl: './movies.component.html',
  styleUrls: ['./movies.component.scss']
})
export class MoviesComponent implements OnInit {

  public movies: Movie[];
  public isLoading: boolean = false;

  imageData: any;

  constructor(
    private moviesService: MoviesService,
    private dialog: MatDialog
  ) { }

  ngOnInit(): void {
    this.loadMovies();

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
    this.isLoading = true;
    this.moviesService.getMovies()
    .subscribe({
      next: (res: Movie[]) => {
        this.movies = res;
        this.isLoading = false;
      },
      error: (err) => {
        console.log(err);
        this.isLoading = false;
      }
    });
  }
}
