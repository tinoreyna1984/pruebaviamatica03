import { Injectable } from '@angular/core';
import { MatSnackBar } from '@angular/material/snack-bar';
import { MessageSnackBarComponent } from '../components/message-snack-bar/message-snack-bar.component';

@Injectable({
  providedIn: 'root'
})
export class HelperService {

  constructor(
    private snackBar: MatSnackBar,
  ) { }

  snackBarMsg(message: any, time: number) {
    this.snackBar.openFromComponent(MessageSnackBarComponent, {
      duration: time,
      data: message,
    });
  }
}
