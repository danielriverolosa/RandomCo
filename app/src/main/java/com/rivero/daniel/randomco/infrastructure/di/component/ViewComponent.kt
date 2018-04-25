package com.rivero.daniel.randomco.infrastructure.di.component

import com.rivero.daniel.randomco.infrastructure.di.module.ViewModule
import com.rivero.daniel.randomco.infrastructure.di.scope.ViewScope
import com.rivero.daniel.randomco.presentation.main.MainActivity
import com.rivero.daniel.randomco.presentation.main.fragment.NearUsersFragment
import com.rivero.daniel.randomco.presentation.main.fragment.RegisterUserFragment
import com.rivero.daniel.randomco.presentation.main.fragment.UserDetailFragment
import com.rivero.daniel.randomco.presentation.main.fragment.UserListFragment
import dagger.Subcomponent


@ViewScope
@Subcomponent(
        modules = [
            ViewModule::class
        ]
)
interface ViewComponent {

    fun inject(activity: MainActivity)

    fun inject(fragment: UserListFragment)
    fun inject(userDetailFragment: UserDetailFragment)
    fun inject(registerUserFragment: RegisterUserFragment)
    fun inject(nearUsersFragment: NearUsersFragment)

}