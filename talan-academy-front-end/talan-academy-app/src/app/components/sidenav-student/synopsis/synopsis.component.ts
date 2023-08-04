import { Component, OnInit } from '@angular/core';
import { ActivatedRoute } from '@angular/router';
import { Synopsis } from 'src/app/models/synopsis.model';
import { SynopsisService } from 'src/app/services/synopsis.service';

@Component({
  selector: 'app-synopsis',
  templateUrl: './synopsis.component.html',
  styleUrls: ['./synopsis.component.scss'],
})
export class SynopsisComponent implements OnInit {
  synopsis!: Synopsis;
  constructor(
    private activatedRoute: ActivatedRoute,
    private synopsisService: SynopsisService
  ) {}

  ngOnInit(): void {
    const lessonId = +this.activatedRoute.snapshot.params['id'];
    this.synopsisService.getSynopsisByLessonId(lessonId).subscribe((res) => {
      const arr = res.description.split('\n');
      arr.forEach((item) => {
        const div = document.createElement('div');
        div.innerText = item;
        document.getElementById('description').appendChild(div);
      });
      const arr2 = res.goals.split('\n');
      arr2.forEach((item) => {
        const div2 = document.createElement('li');
        div2.innerText = item;
        document.getElementById('goals').appendChild(div2);
      });
      this.synopsis = res;
    });
  }
}
