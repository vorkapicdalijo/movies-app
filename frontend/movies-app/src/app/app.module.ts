import { MoviesComponent } from './pages/movies/movies.component';
import { BrowserModule } from '@angular/platform-browser';
import { NgModule } from '@angular/core';

import { AppRoutingModule } from './app-routing.module';
import { AppComponent } from './app.component';
import { HttpClientModule } from '@angular/common/http';
import { BrowserAnimationsModule } from '@angular/platform-browser/animations';
import { FormsModule, ReactiveFormsModule } from '@angular/forms';
import { MatInputModule } from '@angular/material/input';
import { MatToolbarModule } from '@angular/material/toolbar';
import { MoviesService } from './services/movies.service';
import { HeaderComponent } from './components/header/header.component';
import { MatRippleModule } from '@angular/material/core';
import { DurationPipe } from './components/pipes/time-duration.pipe';
import {MatDialogModule} from '@angular/material/dialog';
import { MovieDetailsDialogComponent } from './components/dialogs/movie-details-dialog/movie-details-dialog.component';
import { LightboxModule } from 'ngx-lightbox';

@NgModule({
  declarations: [
    AppComponent,
    MoviesComponent,
    HeaderComponent,
    DurationPipe,
    MovieDetailsDialogComponent
  ],
  imports: [
    BrowserModule,
    AppRoutingModule,
    HttpClientModule,
    BrowserAnimationsModule,
    FormsModule,
    ReactiveFormsModule,
    MatInputModule,
    MatToolbarModule,
    MatRippleModule,
    MatDialogModule,
    LightboxModule
  ],
  providers: [MoviesService],
  bootstrap: [AppComponent]
})
export class AppModule { }
