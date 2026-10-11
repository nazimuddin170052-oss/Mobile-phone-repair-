package com.example.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.model.SubscriptionPlan
import com.example.model.SubscriptionTier
import com.example.ui.theme.*
import com.example.viewmodel.ZukiViewModel

@Composable
fun PremiumSubscriptionScreen(viewModel: ZukiViewModel) {
  val entitlement by viewModel.userEntitlement.collectAsState()
  val paymentNotice by viewModel.paymentNotice.collectAsState()
  val plans = viewModel.subscriptionPlans

  LazyColumn(
    modifier = Modifier
      .fillMaxSize()
      .padding(horizontal = 16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
    contentPadding = PaddingValues(vertical = 16.dp)
  ) {
    // 1. Current Status Header
    item {
      Card(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Navy800)
      ) {
        Column(
          modifier = Modifier.padding(18.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = "ZUKI প্রিমিয়াম সার্ভিসিং ক্লাব",
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp,
                color = Color.White
              )
              Text(
                text = "পেশাদার মোবাইল টেকনিশিয়ানদের জন্য বিশেষ সুবিধা",
                fontSize = 12.sp,
                color = TurquoiseBright
              )
            }

            Surface(
              color = if (entitlement.isPremiumActive) EmeraldSuccess else TurquoiseContainer,
              shape = RoundedCornerShape(8.dp)
            ) {
              Text(
                text = entitlement.tier.labelBengali,
                fontWeight = FontWeight.Bold,
                fontSize = 12.sp,
                color = Navy900,
                modifier = Modifier.padding(horizontal = 10.dp, vertical = 5.dp)
              )
            }
          }

          if (entitlement.isPremiumActive) {
            Text(
              text = "আপনার প্রিমিয়াম সাবস্ক্রিপশন সক্রিয় রয়েছে। সব বিজ্ঞাপন নিষ্ক্রিয় এবং আনলিমিটেড AI ফিচার উন্মুক্ত।",
              fontSize = 13.sp,
              color = EmeraldSuccess
            )
          } else {
            Text(
              text = "ফ্রি সংস্করণে দৈনিক ৩টি AI ডায়াগনসিস ও ৩টি ফটো স্ক্যান অন্তর্ভুক্ত। ১০০% বিজ্ঞাপনমুক্ত ও আনলিমিটেড ব্যবহারের জন্য প্রিমিয়ামে আপগ্রেড করুন।",
              fontSize = 13.sp,
              color = Slate300
            )
          }
        }
      }
    }

    // 2. Payment/Sandbox Notice Banner
    if (paymentNotice != null) {
      item {
        Card(
          modifier = Modifier.fillMaxWidth(),
          shape = RoundedCornerShape(12.dp),
          colors = CardDefaults.cardColors(containerColor = TurquoiseContainer)
        ) {
          Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp)
          ) {
            Icon(Icons.Default.Verified, contentDescription = null, tint = Navy900)
            Text(text = paymentNotice!!, fontSize = 13.sp, color = Navy900, modifier = Modifier.weight(1f))
            IconButton(onClick = { viewModel.clearPaymentNotice() }) {
              Icon(Icons.Default.Close, contentDescription = "বন্ধ করুন", tint = Navy900)
            }
          }
        }
      }
    }

    // 3. Clear Sandbox Disclosure Notice
    item {
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(10.dp))
          .background(Navy700.copy(alpha = 0.5f))
          .border(1.dp, Navy600, RoundedCornerShape(10.dp))
          .padding(12.dp)
      ) {
        Row(
          verticalAlignment = Alignment.Top,
          horizontalArrangement = Arrangement.spacedBy(8.dp)
        ) {
          Icon(Icons.Default.BugReport, contentDescription = null, tint = TurquoiseBright, modifier = Modifier.size(18.dp))
          Column {
            Text(
              text = "Google Play Billing টেস্ট স্যান্ডবক্স (Sandbox Mode)",
              fontWeight = FontWeight.Bold,
              fontSize = 12.sp,
              color = TurquoiseBright
            )
            Text(
              text = "এটি একটি নিরাপদ টেস্ট বিলিং ইন্টারফেস। বোতাম চাপলে কোনো টাকা কর্তন হবে না, স্যান্ডবক্স টেস্টে তাৎক্ষণিক প্রিমিয়াম ফিচার আনলক হয়ে বিজ্ঞাপন বন্ধ হবে।",
              fontSize = 11.sp,
              color = Slate300
            )
          }
        }
      }
    }

    // 4. Subscription Plans
    item {
      Text(
        text = "প্যাকেজ নির্বাচন করুন:",
        style = MaterialTheme.typography.titleMedium,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onBackground
      )
    }

    items(plans) { plan ->
      Card(
        modifier = Modifier
          .fillMaxWidth()
          .testTag("plan_card_${plan.id}"),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(
          containerColor = if (plan.isPopular) TurquoiseContainer else MaterialTheme.colorScheme.surface
        ),
        elevation = CardDefaults.cardElevation(defaultElevation = 3.dp)
      ) {
        Column(
          modifier = Modifier.padding(16.dp),
          verticalArrangement = Arrangement.spacedBy(10.dp)
        ) {
          Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
          ) {
            Column {
              Text(
                text = plan.titleBengali,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                color = if (plan.isPopular) Navy900 else MaterialTheme.colorScheme.onSurface
              )
              Text(
                text = plan.periodBengali,
                fontSize = 12.sp,
                color = if (plan.isPopular) Navy800 else Slate600
              )
            }

            Text(
              text = plan.priceBengali,
              fontWeight = FontWeight.Black,
              fontSize = 18.sp,
              color = if (plan.isPopular) Navy900 else TurquoisePrimary
            )
          }

          HorizontalDivider(color = if (plan.isPopular) Navy800.copy(alpha = 0.2f) else MaterialTheme.colorScheme.surfaceVariant)

          // Features List
          plan.features.forEach { feature ->
            Row(
              verticalAlignment = Alignment.CenterVertically,
              horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
              Icon(
                imageVector = Icons.Default.Check,
                contentDescription = null,
                tint = if (plan.isPopular) Navy900 else EmeraldSuccess,
                modifier = Modifier.size(16.dp)
              )
              Text(
                text = feature,
                fontSize = 13.sp,
                color = if (plan.isPopular) Navy900 else MaterialTheme.colorScheme.onSurface
              )
            }
          }

          Spacer(modifier = Modifier.height(4.dp))

          // Purchase / Subscribe Button
          Button(
            onClick = { viewModel.simulatePurchasePlan(plan.id) },
            modifier = Modifier
              .fillMaxWidth()
              .testTag("purchase_plan_${plan.id}"),
            colors = ButtonDefaults.buttonColors(
              containerColor = if (plan.isPopular) Navy900 else TurquoisePrimary,
              contentColor = if (plan.isPopular) Color.White else Navy900
            ),
            shape = RoundedCornerShape(12.dp)
          ) {
            Icon(Icons.Default.ShoppingCart, contentDescription = null, modifier = Modifier.size(16.dp))
            Spacer(modifier = Modifier.width(6.dp))
            Text(
              text = if (entitlement.isPremiumActive) "পুনরায় সাবস্ক্রাইব করুন" else "স্যান্ডবক্স টেস্টে সক্রিয় করুন",
              fontWeight = FontWeight.Bold,
              fontSize = 13.sp
            )
          }
        }
      }
    }

    // 5. Cancel / Downgrade Section if Premium
    if (entitlement.isPremiumActive) {
      item {
        OutlinedButton(
          onClick = { viewModel.cancelSubscription() },
          modifier = Modifier.fillMaxWidth().testTag("cancel_subscription_btn"),
          shape = RoundedCornerShape(12.dp),
          colors = ButtonDefaults.outlinedButtonColors(contentColor = CrimsonDanger)
        ) {
          Icon(Icons.Default.Cancel, contentDescription = null)
          Spacer(modifier = Modifier.width(6.dp))
          Text("সাবস্ক্রিপশন বাতিল করুন (ফ্রি সংস্করণে ফিরুন)")
        }
      }
    }

    // 6. Google Play Policy Statement
    item {
      Text(
        text = "গুগল প্লে স্টোর পলিসি ও শর্তাবলী অনুযায়ী সাবস্ক্রিপশন স্বয়ংক্রিয়ভাবে নবায়নযোগ্য এবং ব্যবহারকারী যেকোনো সময় অ্যাকাউন্ট সেটিংস থেকে এটি বাতিল করতে পারবেন।",
        fontSize = 11.sp,
        lineHeight = 15.sp,
        color = Slate600
      )
    }
  }
}
