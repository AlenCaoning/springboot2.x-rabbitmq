/**
 * @author: Alen Cao
 * @description:
 * @time: 2019/7/2 13:37
 */

package com.alen.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan(value = {"com.alen.*"})
public class MainConfig
{
}
