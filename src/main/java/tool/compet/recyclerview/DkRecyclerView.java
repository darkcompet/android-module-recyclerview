/*
 * Copyright (c) 2017-2021 DarkCompet. All rights reserved.
 */

package tool.compet.recyclerview;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.ViewDataBinding;
import androidx.recyclerview.widget.RecyclerView;

/**
 * This extends {@link RecyclerView}, provides default {@link BindingViewHolder} and {@link BindingAdapter}
 * to not only reduce boiler code, but also adapt databinding for item view. Note that, your layout
 * must be wrapped with `layout` tag and the project, modules must support databinding.
 * <p></p>
 * The usage is simple, just setup as normal recyclerview, or call chain.
 * Note that, we can override methods inside {@link RecyclerView.Adapter} as we want to take full control,
 * since {@link BindingAdapter} is subclass of it.
 * <pre><code>
 * 	dkrecyclerview
 * 		.setAsLinearLayoutWithFixedItemDimension(context, true)
 * 		.setAdapter(new DkRecyclerView.BindingAdapter<YourItemViewDataBinding>() {
 * 			public YourItemViewDataBinding onCreateItem(ViewGroup parent, int viewType) {
 * 				return YourItemViewDataBinding.inflate(LayoutInflater.from(context), parent, false);
 * 			}
 *
 * 			public void onBindItem(YourItemViewDataBinding binding, int position) {
 * 				DisplayItem item = items.get(position);
 * 				binding.rdChoose.setChecked(item.checked);
 * 				binding.tvName.setTextColor(item.name);
 * 			}
 *
 * 			public int getItemCount() {
 * 				return items.size();
 * 			}
 * 		});
 * </code></pre>
 */
public class DkRecyclerView extends RecyclerView {
	public DkRecyclerView(@NonNull Context context) {
		super(context);
	}

	public DkRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs) {
		super(context, attrs);
	}

	public DkRecyclerView(@NonNull Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);
	}

	/**
	 * ViewHolder with binding for item.
	 *
	 * @param <B> ViewDataBinding for item.
	 */
	public static class BindingViewHolder<B extends ViewDataBinding> extends RecyclerView.ViewHolder {
		public final B binding;

		public BindingViewHolder(View itemView, B binding) {
			super(itemView);
			this.binding = binding;
		}
	}

	/**
	 * Adapter with binding for item.
	 *
	 * @param <B> ViewDataBinding for item.
	 */
	public static abstract class BindingAdapter<B extends ViewDataBinding> extends RecyclerView.Adapter<BindingViewHolder<B>> {
		/**
		 * Create ViewDataBinding for item view.
		 * To create new item binding, you can call: `YourItemLayoutBinding.inflate(LayoutInflater.from(context))`
		 */
		public abstract B onCreateViewBinding(ViewGroup parent, int viewType);

		/**
		 * Called when viewHolder is created.
		 * Caller can access `binding` inside passed `viewHodler` to do something as setItemClickListener.
		 */
		protected abstract void onViewHolderCreated(BindingViewHolder<B> holder);

		@Override
		public abstract void onBindViewHolder(@NonNull BindingViewHolder<B> holder, int position);

		@Override
		public abstract int getItemCount();

		@NonNull
		@Override
		public BindingViewHolder<B> onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
			B binding = this.onCreateViewBinding(parent, viewType);

			BindingViewHolder<B> viewHolder = new BindingViewHolder<>(binding.getRoot(), binding);
			onViewHolderCreated(viewHolder);

			return viewHolder;
		}
	}
}
