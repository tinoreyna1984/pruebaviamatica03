import { Component, OnInit } from '@angular/core';
import { AtencionesService } from '../../services/atenciones.service';

@Component({
  selector: 'app-atenciones-page',
  templateUrl: './atenciones-page.component.html',
  styleUrls: ['./atenciones-page.component.css']
})
export class AtencionesPageComponent implements OnInit {
  constructor(private atencionesService: AtencionesService){}

  ngOnInit(): void {
    this.atencionesService.listarAtenciones().subscribe(
      {
        next: (res: any) => {
          console.log(res);
        },
        error: (e: any) => {
          console.log(e);
        },
      }
    );
  }

}
