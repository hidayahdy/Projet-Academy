import { NgModule } from '@angular/core';
import { CommonModule } from '@angular/common';

import { RegistredRoutingModule } from './registred-routing.module';
import { DashboardRegistredComponent } from './dashboard-registred/dashboard-registred.component';
import { MaterialModule } from '../material/material/material.module';

@NgModule({
  declarations: [DashboardRegistredComponent],
  imports: [CommonModule, RegistredRoutingModule, MaterialModule],
})
export class RegistredModule {}
