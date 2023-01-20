/*
 * Copyright 2022 Joel Kanyi.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.kanyideveloper.mealtime

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.google.accompanist.navigation.animation.rememberAnimatedNavController
import com.google.accompanist.navigation.material.ExperimentalMaterialNavigationApi
import com.kanyideveloper.compose_ui.theme.MealTimeTheme
import com.kanyideveloper.compose_ui.theme.Theme
import com.kanyideveloper.favorites.presentation.favorites.presentation.destinations.FavoritesScreenDestination
import com.kanyideveloper.mealplanner.destinations.MealPlannerScreenDestination
import com.kanyideveloper.mealtime.component.StandardScaffold
import com.kanyideveloper.mealtime.component.navGraph
import com.kanyideveloper.mealtime.navigation.CoreFeatureNavigator
import com.kanyideveloper.mealtime.navigation.NavGraphs
import com.kanyideveloper.mealtime.navigation.scaleInEnterTransition
import com.kanyideveloper.mealtime.navigation.scaleInPopEnterTransition
import com.kanyideveloper.mealtime.navigation.scaleOutExitTransition
import com.kanyideveloper.mealtime.navigation.scaleOutPopExitTransition
import com.kanyideveloper.presentation.destinations.HomeScreenDestination
import com.kanyideveloper.search.presentation.search.destinations.SearchScreenDestination
import com.kanyideveloper.settings.presentation.destinations.SettingsScreenDestination
import com.ramcosta.composedestinations.DestinationsNavHost
import com.ramcosta.composedestinations.animations.defaults.NestedNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.defaults.RootNavGraphDefaultAnimations
import com.ramcosta.composedestinations.animations.rememberAnimatedNavHostEngine
import com.ramcosta.composedestinations.navigation.dependency
import com.ramcosta.composedestinations.scope.DestinationScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @OptIn(ExperimentalAnimationApi::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        installSplashScreen()
        setContent {
            val viewModel: MainViewModel = hiltViewModel()

            val themeValue by viewModel.theme.collectAsState(
                initial = Theme.FOLLOW_SYSTEM.themeValue,
                context = Dispatchers.Main.immediate
            )

            MealTimeTheme(
                theme = themeValue
            ) {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    val navController = rememberAnimatedNavController()
                    val newBackStackEntry by navController.currentBackStackEntryAsState()
                    val route = newBackStackEntry?.destination?.route

                    StandardScaffold(
                        navController = navController,
                        showBottomBar = route in listOf(
                            "home/${HomeScreenDestination.route}",
                            "search/${SearchScreenDestination.route}",
                            "meal_planner/${MealPlannerScreenDestination.route}",
                            "favorites/${FavoritesScreenDestination.route}",
                            "settings/${SettingsScreenDestination.route}"
                        )
                    ) { innerPadding ->
                        Box(modifier = Modifier.padding(innerPadding)) {
                            AppNavigation(
                                navController = navController,
                                modifier = Modifier
                                    .fillMaxSize()
                            )
                        }
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterialNavigationApi::class)
@ExperimentalAnimationApi
@Composable
internal fun AppNavigation(
    navController: NavHostController,
    modifier: Modifier = Modifier
) {
    val navHostEngine = rememberAnimatedNavHostEngine(
        navHostContentAlignment = Alignment.TopCenter,
        rootDefaultAnimations = RootNavGraphDefaultAnimations.ACCOMPANIST_FADING, // default `rootDefaultAnimations` means no animations
        defaultAnimationsForNestedNavGraph = mapOf(
            NavGraphs.home to NestedNavGraphDefaultAnimations(
                enterTransition = {
                    scaleInEnterTransition()
                },
                exitTransition = {
                    scaleOutExitTransition()
                },
                popEnterTransition = {
                    scaleInPopEnterTransition()
                },
                popExitTransition = {
                    scaleOutPopExitTransition()
                }
            ),
            NavGraphs.search to NestedNavGraphDefaultAnimations(
                enterTransition = {
                    scaleInEnterTransition()
                },
                exitTransition = {
                    scaleOutExitTransition()
                },
                popEnterTransition = {
                    scaleInPopEnterTransition()
                },
                popExitTransition = {
                    scaleOutPopExitTransition()
                }
            ),
            NavGraphs.favorites to NestedNavGraphDefaultAnimations(
                enterTransition = {
                    scaleInEnterTransition()
                },
                exitTransition = {
                    scaleOutExitTransition()
                },
                popEnterTransition = {
                    scaleInPopEnterTransition()
                },
                popExitTransition = {
                    scaleOutPopExitTransition()
                }
            ),
            NavGraphs.settings to NestedNavGraphDefaultAnimations(
                enterTransition = {
                    scaleInEnterTransition()
                },
                exitTransition = {
                    scaleOutExitTransition()
                },
                popEnterTransition = {
                    scaleInPopEnterTransition()
                },
                popExitTransition = {
                    scaleOutPopExitTransition()
                }
            ),
            NavGraphs.mealPlanner to NestedNavGraphDefaultAnimations(
                enterTransition = {
                    scaleInEnterTransition()
                },
                exitTransition = {
                    scaleOutExitTransition()
                },
                popEnterTransition = {
                    scaleInPopEnterTransition()
                },
                popExitTransition = {
                    scaleOutPopExitTransition()
                }
            )
        )
    )

    DestinationsNavHost(
        engine = navHostEngine,
        navController = navController,
        navGraph = NavGraphs.root,
        modifier = modifier,
        dependenciesContainerBuilder = {
            dependency(currentNavigator())
        }
    )
}

fun DestinationScope<*>.currentNavigator(/*openSettings: () -> Unit*/): CoreFeatureNavigator {
    return CoreFeatureNavigator(
        navBackStackEntry.destination.navGraph(),
        navController
    )
}
