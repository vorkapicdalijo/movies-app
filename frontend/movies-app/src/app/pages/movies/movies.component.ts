import { Component, OnInit } from '@angular/core';
import { DomSanitizer } from '@angular/platform-browser';
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
    private sanitizer: DomSanitizer,
  ) { }

  ngOnInit(): void {
    this.loadMovies();

  }

  private loadMovies() {
    this.isLoading = true;
    this.moviesService.getMovies()
    .subscribe({
      next: (res: Movie[]) => {
        this.movies = res;
        this.isLoading = false;
        this.handleImage();
      },
      error: (err) => {
        console.log(err);
        this.isLoading = false;
      }
  });
  }

  handleImage() {
    this.imageData = this.sanitizer.bypassSecurityTrustUrl(`data:image/jpg;base64, ${this.movies[0].titleImage.fileContent}`);
  }

  sanitize( url:string ) {
    return this.sanitizer.bypassSecurityTrustUrl(url);
  }

}
