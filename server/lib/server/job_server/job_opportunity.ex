defmodule Server.JobServer.JobOpportunity do
  use Ecto.Schema
  import Ecto.Changeset

  @derive {Jason.Encoder, only: [:area, :contato, :nomeEmpresa, :salario, :tempo]}

  schema "opportunities" do
    field :area, :string
    field :contato, :string
    field :nomeEmpresa, :string
    field :salario, :integer
    field :tempo, :integer

    timestamps()
  end

  @doc false
  def changeset(job_opportunity, attrs) do
    job_opportunity
    |> cast(attrs, [:nomeEmpresa, :contato, :area, :tempo, :salario])
    |> validate_required([:nomeEmpresa, :contato, :area, :tempo, :salario])
  end
end
