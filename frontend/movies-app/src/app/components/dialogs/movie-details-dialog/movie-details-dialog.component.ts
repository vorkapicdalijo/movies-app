import { Component, Inject, OnInit } from '@angular/core';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { MovieImage } from 'src/app/models/movie-image.model';
import { Movie } from 'src/app/models/movie.model';
import { MoviesService } from 'src/app/services/movies.service';

export interface MovieDialogData {
  movieId: number;
}

@Component({
  selector: 'app-movie-details-dialog',
  templateUrl: './movie-details-dialog.component.html',
  styleUrls: ['./movie-details-dialog.component.scss']
})
export class MovieDetailsDialogComponent implements OnInit {

  private movieId!: number;
  public movie!: Movie;

  public isLoading: boolean = false;

  constructor(
    private dialogRef: MatDialogRef<MovieDetailsDialogComponent>,
    @Inject(MAT_DIALOG_DATA) public data: MovieDialogData,
    private moviesService: MoviesService,
  ) {
    if (data.movieId) {
      this.movieId = data.movieId;

      this.loadMovieDetails();
    }
   }

  ngOnInit(): void {
  }

  loadMovieDetails() {
    this.isLoading = true;
    this.moviesService.getMovieDetailsByMovieId(this.movieId)
      .subscribe({
        next: (movie: Movie) => {
          this.movie = movie;

          this.isLoading = false;
        },
        error: (err) => {
          console.error(err);

          this.isLoading = false;
        }
      });
  }

  closeDialog() {
    this.dialogRef.close();
  }

}
